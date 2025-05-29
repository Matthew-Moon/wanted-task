package com.wanted.presentation

import com.wanted.domain.model.CompanyInfoModel
import com.wanted.domain.model.GeoLocationModel
import com.wanted.domain.model.ImageModel
import com.wanted.domain.model.LocationModel
import com.wanted.domain.model.LogoUrlModel
import com.wanted.domain.model.ViewportModel
import com.wanted.domain.model.ViewportPositionModel
import com.wanted.domain.result.DomainResult
import com.wanted.domain.result.ErrorBody
import com.wanted.domain.usecase.GetCompanyDetailUseCase
import com.wanted.presentation.detail.CompanyDetailUiState
import com.wanted.presentation.detail.CompanyDetailViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CompanyDetailViewModelTest {

    private lateinit var viewModel: CompanyDetailViewModel
    private lateinit var mockUseCase: GetCompanyDetailUseCase
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        mockUseCase = mockk()
        viewModel = CompanyDetailViewModel(mockUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadCompanyDetail 호출 시 성공하면 Success 상태로 전이됨`() = runTest {
        // Given
        val dummyCompany = CompanyInfoModel(
            id = 1,
            name = "원티드랩",
            description = "테스트 기업입니다.",
            address = "서울시 강남구",
            companyConfirm = true,
            geoLocation = GeoLocationModel(
                location = LocationModel(37.0, 127.0),
                locationType = "ROOFTOP",
                viewport = ViewportModel(
                    northeast = ViewportPositionModel(37.1, 127.1),
                    southwest = ViewportPositionModel(36.9, 126.9)
                )
            ),
            images = listOf(ImageModel.placeholder()),
            link = "https://www.wantedlab.com",
            logoUrl = LogoUrlModel("origin.png", "thumb.png"),
            registrationNumber = "123-45-67890",
            url = "https://www.wanted.co.kr/company/wantedlab"
        )
        coEvery { mockUseCase(1) } returns DomainResult.Success(dummyCompany)

        // When
        viewModel.loadCompanyDetail(1)
        advanceUntilIdle()

        // Then
        val state = viewModel.uiState.value
        assertTrue(state is CompanyDetailUiState.Success)
        assertEquals(dummyCompany, (state as CompanyDetailUiState.Success).company)
        println("### 상태: $state")

    }

    @Test
    fun `loadCompanyDetail 호출 시 실패하면 Error 상태로 전이됨`() = runTest {
        // Given
        val errorMessage = "네트워크 오류"
        val error = ErrorBody(errorCode = "NETWORK_ERROR", message = errorMessage)

        coEvery { mockUseCase(2) } returns DomainResult.Failure(error)
        // When
        viewModel.loadCompanyDetail(2)
        advanceUntilIdle()

        // Then
        val state = viewModel.uiState.value
        assertTrue(state is CompanyDetailUiState.Error)
        assertEquals(errorMessage, (state as CompanyDetailUiState.Error).message)
    }
}

//
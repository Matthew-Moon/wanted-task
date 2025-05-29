package com.wanted.presentation

import com.wanted.domain.model.CompanyModel
import com.wanted.domain.model.LogoImgModel
import com.wanted.domain.model.PagedCompanyModel
import com.wanted.domain.model.TitleImgModel
import com.wanted.domain.result.DomainResult
import com.wanted.domain.result.ErrorBody
import com.wanted.domain.usecase.GetCompanyListUseCase
import com.wanted.presentation.list.CompanyListUiState
import com.wanted.presentation.list.CompanyListViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CompanyListViewModelTest {

    private lateinit var mockUseCase: GetCompanyListUseCase
    private lateinit var viewModel: CompanyListViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        mockUseCase = mockk()
        viewModel = CompanyListViewModel(mockUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `초기 상태는 EmptyQuery`() {
        assertTrue(viewModel.uiState.value is CompanyListUiState.EmptyQuery)
    }

    @Test
    fun `빈 문자열 입력 시 EmptyQuery 상태로 전이`() = runTest {
        viewModel.searchCompany("")
        advanceUntilIdle()
        assertTrue(viewModel.uiState.value is CompanyListUiState.EmptyQuery)
    }

    @Test
    fun `공백만 있는 문자열 입력 시 EmptyQuery 상태로 전이`() = runTest {
        viewModel.searchCompany("    ")
        advanceUntilIdle()
        assertTrue(viewModel.uiState.value is CompanyListUiState.EmptyQuery)
    }

    @Test
    fun `검색 성공 시 Success 상태로 전이`() = runTest {
        val testCompanies = listOf(createTestCompany(1, "원티드랩"))
        coEvery { mockUseCase("원티드랩", 0, 20) } returns DomainResult.Success(
            PagedCompanyModel(testCompanies, nextOffset = null, prevOffset = null)
        )

        viewModel.searchCompany("원티드랩")
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is CompanyListUiState.Success)
        assertEquals("원티드랩", (state as CompanyListUiState.Success).companies.first().name)
    }

    @Test
    fun `검색 실패 시 Error 상태로 전이`() = runTest {
        coEvery {
            mockUseCase("fail", 0, 20)
        } returns DomainResult.Failure(ErrorBody("ERR", "에러 발생"))

        viewModel.searchCompany("fail")
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is CompanyListUiState.Error)
        assertEquals("에러 발생", (state as CompanyListUiState.Error).message)
    }

    @Test
    fun `loadNextPage 호출 시 다음 페이지 데이터 추가`() = runTest {
        val firstPage = (1..20).map { createTestCompany(it, "Company $it") }
        val secondPage = (21..25).map { createTestCompany(it, "Company $it") }

        coEvery { mockUseCase("원티드랩", 0, 20) } returns DomainResult.Success(
            PagedCompanyModel(firstPage, nextOffset = 20, prevOffset = null)
        )
        coEvery { mockUseCase("원티드랩", 20, 20) } returns DomainResult.Success(
            PagedCompanyModel(secondPage, nextOffset = null, prevOffset = null)
        )

        viewModel.searchCompany("원티드랩")
        advanceUntilIdle()

        viewModel.loadNextPage()
        advanceUntilIdle()

        val state = viewModel.uiState.value as CompanyListUiState.Success
        assertEquals(25, state.companies.size)
        assertEquals("Company 25", state.companies.last().name)
    }

    @Test
    fun `loadNextPage 호출 시 nextOffset 없으면 아무 일도 안함`() = runTest {
        val companies = (1..10).map { createTestCompany(it, "C$it") }

        coEvery {
            mockUseCase("none", 0, 20)
        } returns DomainResult.Success(
            PagedCompanyModel(companies, nextOffset = null, prevOffset = null)
        )

        viewModel.searchCompany("none")
        advanceUntilIdle()

        viewModel.loadNextPage()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertEquals(10, (state as CompanyListUiState.Success).companies.size)
    }

    private fun createTestCompany(
        id: Int,
        name: String,
        description: String = "description",
        logoUrl: String = "",
        titleUrl: String = "",
        companyUrl: String = ""
    ): CompanyModel {
        return CompanyModel(
            id = id,
            name = name,
            description = description,
            logoImg = LogoImgModel(origin = logoUrl, thumb = logoUrl),
            titleImg = TitleImgModel(origin = titleUrl, thumb = titleUrl),
            url = companyUrl
        )
    }
}

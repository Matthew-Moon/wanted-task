package com.wanted.presentation.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wanted.presentation.theme.WantedTaskTheme
import com.wanted.presentation.theme.White

@Composable
fun CompanySearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    onImeActionSearch: () -> Unit,
    placeholder: String = "텍스트를 입력하세요",
    trailingIcon: @Composable (() -> Unit)
) {
    val textStyle = TextStyle(fontSize = 16.sp)
    val keyboardController = LocalSoftwareKeyboardController.current

    Column {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            textStyle = textStyle,
            modifier = Modifier
                .fillMaxWidth()
                .background(White)
                .padding(horizontal = 12.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    keyboardController?.hide()
                    onImeActionSearch()
                }
            ),
            decorationBox = { innerTextField ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 56.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (value.isEmpty()) {
                            Text(
                                text = placeholder,
                                style = textStyle,
                                color = Color.LightGray
                            )
                        }
                        innerTextField()
                    }

                    if (value.isNotBlank()) {
                        Spacer(modifier = Modifier.width(8.dp))
                        trailingIcon()
                    }
                }
            }
        )
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            color = Color(0xFFE0E0E0),
            thickness = 1.dp
        )

    }
}


@Preview(showBackground = true)
@Composable
fun CompanySearchBarPreview() {
    WantedTaskTheme {
        var query by remember { mutableStateOf("z원티d") }

        CompanySearchBar(
            value = query,
            onValueChange = {},
            onImeActionSearch = {},
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "지우기",
                    modifier = Modifier
                        .size(20.dp)
                        .clickable { query = "" },
                    tint = Color.Gray
                )
            }
        )
    }
}

package com.wanted.presentation.detail

import android.content.Intent
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.core.net.toUri

@Composable
fun DescriptionText(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle
) {
    val context = LocalContext.current
    val annotatedText = remember(text) {
        buildAnnotatedString {
            val regex = Regex("""(https?://[^\s]+)|(www\.[^\s]+)""")
            var lastIndex = 0

            for (match in regex.findAll(text)) {
                val start = match.range.first
                val end = match.range.last + 1

                append(text.substring(lastIndex, start))

                val url = match.value
                pushStringAnnotation(tag = "URL", annotation = url)
                withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                    append(url)
                }
                pop()

                lastIndex = end
            }

            if (lastIndex < text.length) append(text.substring(lastIndex))

        }
    }

    var layoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }

    Text(
        text = annotatedText,
        style = style,
        modifier = modifier
            .pointerInput(Unit) {
                detectTapGestures { offsetPosition ->
                    layoutResult?.let { layout ->
                        val offset = layout.getOffsetForPosition(offsetPosition)
                        annotatedText.getStringAnnotations("URL", offset, offset)
                            .firstOrNull()?.let { annotation ->
                                val finalUrl = if (annotation.item.startsWith("www.")) {
                                    "https://${annotation.item}"
                                } else {
                                    annotation.item
                                }
                                val intent = Intent(Intent.ACTION_VIEW, finalUrl.toUri())
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                context.startActivity(intent, null)
                            }
                    }
                }
            },
        onTextLayout = { layoutResult = it }
    )
}

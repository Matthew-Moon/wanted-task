package com.wanted.presentation.detail

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.wanted.presentation.theme.SkyBlue

@Composable
fun HyperlinkText(
    modifier: Modifier,
    title: String,
    url: String
) {
    val context = LocalContext.current

    Column {
        Text(
            modifier = modifier.padding(top = 24.dp),
            text = title
        )
        Text(
            modifier = modifier.clickable {
                val finalUrl =
                    if (url.startsWith("www.")) "https://$url"
                    else url

                val intent = Intent(Intent.ACTION_VIEW, finalUrl.toUri())
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent, null)
            },
            text = url,
            color = SkyBlue,
            textDecoration = TextDecoration.Underline,
        )

    }
}

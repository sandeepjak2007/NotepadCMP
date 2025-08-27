package com.sandeep.notepad.ui

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import notepadcmp.composeapp.generated.resources.FunnelDisplay_Bold
import notepadcmp.composeapp.generated.resources.FunnelDisplay_ExtraBold
import notepadcmp.composeapp.generated.resources.FunnelDisplay_Light
import notepadcmp.composeapp.generated.resources.FunnelDisplay_Medium
import notepadcmp.composeapp.generated.resources.FunnelDisplay_Regular
import notepadcmp.composeapp.generated.resources.FunnelDisplay_SemiBold
import notepadcmp.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.Font

@Composable
private fun funnelFontFamily() = FontFamily(
    Font(Res.font.FunnelDisplay_Light, weight = FontWeight.Light),
    Font(Res.font.FunnelDisplay_Regular, weight = FontWeight.Normal),
    Font(Res.font.FunnelDisplay_Medium, weight = FontWeight.Medium),
    Font(Res.font.FunnelDisplay_Bold, weight = FontWeight.Bold),
    Font(Res.font.FunnelDisplay_ExtraBold, weight = FontWeight.ExtraBold),
    Font(Res.font.FunnelDisplay_SemiBold, weight = FontWeight.SemiBold)
)

@Composable
fun funnelTypography(): Typography {
    val funnel = funnelFontFamily()
    return Typography().copy(
        displayLarge = Typography().displayLarge.copy(fontFamily = funnel),
        displayMedium = Typography().displayMedium.copy(fontFamily = funnel),
        displaySmall = Typography().displaySmall.copy(fontFamily = funnel),
        headlineLarge = Typography().headlineLarge.copy(fontFamily = funnel),
        headlineMedium = Typography().headlineMedium.copy(fontFamily = funnel),
        headlineSmall = Typography().headlineSmall.copy(fontFamily = funnel),
        titleLarge = Typography().titleLarge.copy(fontFamily = funnel),
        titleMedium = Typography().titleMedium.copy(fontFamily = funnel),
        titleSmall = Typography().titleSmall.copy(fontFamily = funnel),
        bodyLarge = Typography().bodyLarge.copy(fontFamily = funnel),
        bodyMedium = Typography().bodyMedium.copy(fontFamily = funnel),
        bodySmall = Typography().bodySmall.copy(fontFamily = funnel),
        labelLarge = Typography().labelLarge.copy(fontFamily = funnel),
        labelMedium = Typography().labelMedium.copy(fontFamily = funnel),
        labelSmall = Typography().labelSmall.copy(fontFamily = funnel)
    )
}
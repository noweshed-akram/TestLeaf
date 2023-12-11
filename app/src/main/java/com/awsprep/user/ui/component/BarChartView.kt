package com.awsprep.user.ui.component

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.barchart.BarChart
import co.yml.charts.ui.barchart.models.BarChartData
import co.yml.charts.ui.barchart.models.BarData
import co.yml.charts.ui.barchart.models.BarStyle
import com.awsprep.user.domain.models.TestResult
import com.awsprep.user.ui.theme.PrimaryColor

/**
 * Created by Md. Noweshed Akram on 1/12/23.
 */
@Composable
fun BarChartView(
    resultList: List<TestResult>
) {

    val maxRange = 60
    val yStepSize = 5
    val barData = mutableListOf<BarData>()

    resultList.forEachIndexed { i, result ->
        barData += BarData(
            point = Point(
                (i + 1).toFloat(),
                result.correctAnswered.toFloat()
            ),
            color = PrimaryColor,
            label = result.testName,
        )
    }

    Log.d("BarChartView: ", barData.toString())

    if (barData.size > 0) {

        val xAxisData = AxisData.Builder()
            .axisStepSize(56.dp)
            .steps(barData.size)
            .bottomPadding(12.dp)
            .axisLabelAngle(12f)
            .startDrawPadding(32.dp)
            .shouldDrawAxisLineTillEnd(true)
            .labelData { index -> barData[index].label }
            .build()

        val yAxisData = AxisData.Builder()
            .steps(yStepSize)
            .labelAndAxisLinePadding(24.dp)
            .axisOffset(24.dp)
            .labelData { index -> (index * (maxRange / yStepSize)).toString() }
            .build()

        val barChartData = BarChartData(
            chartData = barData,
            xAxisData = xAxisData,
            yAxisData = yAxisData,
            barStyle = BarStyle(
                cornerRadius = 0.dp,
                paddingBetweenBars = 36.dp,
                barWidth = 24.dp
            ),
            showYAxis = true,
            showXAxis = true,
            horizontalExtraSpace = 24.dp
        )

        BarChart(
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth(),
            barChartData = barChartData
        )
    }

}
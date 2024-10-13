package com.testleaf.user.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.piechart.charts.DonutPieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import com.testleaf.user.ui.theme.ColorAccent
import com.testleaf.user.ui.theme.ErrorColor
import com.testleaf.user.ui.theme.ErrorColorLight
import com.testleaf.user.ui.theme.GreyColor
import com.testleaf.user.ui.theme.PrimaryColor
import com.testleaf.user.ui.theme.SecondaryColor
import com.testleaf.user.ui.theme.SecondaryColorLight
import com.testleaf.user.ui.theme.StrokeColor
import com.testleaf.user.ui.theme.Typography
import com.testleaf.user.utils.AppConstant.STATUS_PASS

/**
 * Created by Md. Noweshed Akram on 29/11/23.
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ResultListItem(
    examType: String = "",
    examName: String = "",
    totalQs: String = "",
    correctAns: String = "",
    testDate: String = "",
    status: String = ""
) {
    Box(
        modifier = Modifier
            .wrapContentHeight()
            .clip(RoundedCornerShape(8.dp))
            .border(
                1.dp,
                StrokeColor,
                RoundedCornerShape(8.dp)
            )
            .clickable {

            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1.0f)
                ) {

                    Text(
                        modifier = Modifier.padding(10.dp),
                        text = examType,
                        style = Typography.bodySmall,
                        color = GreyColor,
                        maxLines = 1
                    )

                    Text(
                        modifier = Modifier.padding(horizontal = 10.dp),
                        text = examName,
                        style = Typography.titleMedium,
                        color = PrimaryColor,
                        maxLines = 1
                    )
                }

                val donutChartData = PieChartData(
                    slices = listOf(
                        PieChartData.Slice("Correct", correctAns.toFloat(), Color(0xFF20BF55)),
                        PieChartData.Slice(
                            "Wrong",
                            (totalQs.toFloat() - correctAns.toFloat()),
                            Color(0xFFF53844)
                        )
                    ),
                    plotType = PlotType.Donut
                )

                val donutChartConfig = PieChartConfig(
                    strokeWidth = 16f,
                    activeSliceAlpha = .9f,
                    isAnimationEnable = false
                )

                Box(
                    modifier = Modifier.wrapContentSize(),
                    contentAlignment = Alignment.Center
                ) {
                    DonutPieChart(
                        modifier = Modifier
                            .width(80.dp)
                            .height(80.dp)
                            .padding(4.dp),
                        donutChartData,
                        donutChartConfig
                    )

                    Text(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        text = ((correctAns.toInt() * 100) / totalQs.toInt()).toString() + "%",
                        style = Typography.labelLarge,
                        color = Color.Black,
                        maxLines = 1
                    )
                }

            }

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp))
                    .background(color = ColorAccent)
            ) {
                Row(
                    modifier = Modifier.padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Column {
                        Text(
                            modifier = Modifier.padding(horizontal = 8.dp),
                            text = "Correct Answer",
                            style = Typography.labelMedium,
                            color = GreyColor,
                            maxLines = 1
                        )

                        Text(
                            modifier = Modifier.padding(horizontal = 8.dp),
                            text = "$correctAns out of $totalQs",
                            style = Typography.labelMedium,
                            color = Color.Black,
                            maxLines = 1
                        )
                    }

                    Box(
                        modifier = Modifier
                            .height(32.dp)
                            .width(1.dp)
                            .background(color = PrimaryColor.copy(alpha = .5f))
                    )

                    Column(
                        modifier = Modifier.weight(1.0f)
                    ) {
                        Text(
                            modifier = Modifier.padding(horizontal = 8.dp),
                            text = "Date",
                            style = Typography.labelMedium,
                            color = GreyColor,
                            maxLines = 1
                        )

                        Text(
                            modifier = Modifier.padding(horizontal = 8.dp),
                            text = testDate,
                            style = Typography.labelMedium,
                            color = Color.Black,
                            maxLines = 1
                        )
                    }

                    Text(
                        modifier = Modifier
                            .background(
                                color = if (status == STATUS_PASS) SecondaryColorLight else ErrorColorLight,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .padding(vertical = 8.dp, horizontal = 16.dp),
                        text = status,
                        style = Typography.bodyLarge,
                        color = if (status == STATUS_PASS) SecondaryColor else ErrorColor,
                        maxLines = 1
                    )

                }

            }

        }
    }
}
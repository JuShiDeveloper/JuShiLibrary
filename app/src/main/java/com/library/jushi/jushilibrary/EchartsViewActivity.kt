package com.library.jushi.jushilibrary

import com.jushi.library.base.BaseFragmentActivity
import com.jushi.library.customView.navigationbar.NavigationBar
import com.jushi.library.utils.LogUtil
import com.jushi.library.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_echarts_layout.*

/**
 * echarts view 示例页面
 */
class EchartsViewActivity : BaseFragmentActivity() {

    override fun navigationBar(): Boolean {
        return true
    }

    override fun initNavigationBar(navBar: NavigationBar) {
        super.initNavigationBar(navBar)
        navBar.setTitleText("Echarts示例")
    }

    override fun getLayoutResId(): Int {
        setSystemBarStatus(true, true, true)
        return R.layout.activity_echarts_layout
    }

    override fun initView() {
        echartsView.setData(getData4(), 350)
        echartsView1.setData(getData1(),400)
        echartsView2.setData(getData3(),320)
        echartsView3.setData(getData())
    }

    override fun initData() {

    }

    override fun setListener() {
        echartsView1.setOnEChartsClickListener {
            LogUtil.v("柱状图点击事件：$it")
            ToastUtil.showToast(this, it.getString("name"))
        }
        echartsView2.setOnEChartsClickListener {
            LogUtil.v("饼图点击事件：$it")
            ToastUtil.showToast(this, it.getString("name"))
        }
    }

    private fun getData(): String {
        return "{\n" +
                "  tooltip: {\n" +
                "    formatter: '{a} <br/>{b} : {c}%'\n" +
                "  },\n" +
                "  series: [\n" +
                "    {\n" +
                "      name: 'Pressure',\n" +
                "      type: 'gauge',\n" +
                "      progress: {\n" +
                "        show: true\n" +
                "      },\n" +
                "      detail: {\n" +
                "        valueAnimation: true,\n" +
                "        formatter: '{value}'\n" +
                "      },\n" +
                "      data: [\n" +
                "        {\n" +
                "          value: 50,\n" +
                "          name: 'SCORE'\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}"
    }

    private fun getData1(): String {
        return "{\n" +
                "  tooltip: {\n" +
                "    trigger: 'axis',\n" +
                "    axisPointer: {\n" +
                "      type: 'shadow'\n" +
                "    }\n" +
                "  },\n" +
                "  legend: {},\n" +
                "  grid: {\n" +
                "    left: '3%',\n" +
                "    right: '4%',\n" +
                "    bottom: '3%',\n" +
                "    containLabel: true\n" +
                "  },\n" +
                "  xAxis: [\n" +
                "    {\n" +
                "      type: 'category',\n" +
                "      data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']\n" +
                "    }\n" +
                "  ],\n" +
                "  yAxis: [\n" +
                "    {\n" +
                "      type: 'value'\n" +
                "    }\n" +
                "  ],\n" +
                "  series: [\n" +
                "    {\n" +
                "      name: 'Direct',\n" +
                "      type: 'bar',\n" +
                "      emphasis: {\n" +
                "        focus: 'series'\n" +
                "      },\n" +
                "      data: [320, 332, 301, 334, 390, 330, 320]\n" +
                "    },\n" +
                "    {\n" +
                "      name: 'Email',\n" +
                "      type: 'bar',\n" +
                "      stack: 'Ad',\n" +
                "      emphasis: {\n" +
                "        focus: 'series'\n" +
                "      },\n" +
                "      data: [120, 132, 101, 134, 90, 230, 210]\n" +
                "    },\n" +
                "    {\n" +
                "      name: 'Union Ads',\n" +
                "      type: 'bar',\n" +
                "      stack: 'Ad',\n" +
                "      emphasis: {\n" +
                "        focus: 'series'\n" +
                "      },\n" +
                "      data: [220, 182, 191, 234, 290, 330, 310]\n" +
                "    },\n" +
                "    {\n" +
                "      name: 'Video Ads',\n" +
                "      type: 'bar',\n" +
                "      stack: 'Ad',\n" +
                "      emphasis: {\n" +
                "        focus: 'series'\n" +
                "      },\n" +
                "      data: [150, 232, 201, 154, 190, 330, 410]\n" +
                "    },\n" +
                "    {\n" +
                "      name: 'Search Engine',\n" +
                "      type: 'bar',\n" +
                "      data: [862, 1018, 964, 1026, 1679, 1600, 1570],\n" +
                "      emphasis: {\n" +
                "        focus: 'series'\n" +
                "      },\n" +
                "      markLine: {\n" +
                "        lineStyle: {\n" +
                "          type: 'dashed'\n" +
                "        },\n" +
                "        data: [[{ type: 'min' }, { type: 'max' }]]\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      name: 'Baidu',\n" +
                "      type: 'bar',\n" +
                "      barWidth: 5,\n" +
                "      stack: 'Search Engine',\n" +
                "      emphasis: {\n" +
                "        focus: 'series'\n" +
                "      },\n" +
                "      data: [620, 732, 701, 734, 1090, 1130, 1120]\n" +
                "    },\n" +
                "    {\n" +
                "      name: 'Google',\n" +
                "      type: 'bar',\n" +
                "      stack: 'Search Engine',\n" +
                "      emphasis: {\n" +
                "        focus: 'series'\n" +
                "      },\n" +
                "      data: [120, 132, 101, 134, 290, 230, 220]\n" +
                "    },\n" +
                "    {\n" +
                "      name: 'Bing',\n" +
                "      type: 'bar',\n" +
                "      stack: 'Search Engine',\n" +
                "      emphasis: {\n" +
                "        focus: 'series'\n" +
                "      },\n" +
                "      data: [60, 72, 71, 74, 190, 130, 110]\n" +
                "    },\n" +
                "    {\n" +
                "      name: 'Others',\n" +
                "      type: 'bar',\n" +
                "      stack: 'Search Engine',\n" +
                "      emphasis: {\n" +
                "        focus: 'series'\n" +
                "      },\n" +
                "      data: [62, 82, 91, 84, 109, 110, 120]\n" +
                "    }\n" +
                "  ]\n" +
                "}"
    }

    private fun getData3(): String {
        return "{\n" +
                "  legend: {\n" +
                "    top: 'bottom'\n" +
                "  },\n" +
                "  toolbox: {\n" +
                "    show: true,\n" +
                "    feature: {\n" +
                "      mark: { show: true },\n" +
                "      dataView: { show: true, readOnly: false },\n" +
                "      restore: { show: true },\n" +
                "      saveAsImage: { show: true }\n" +
                "    }\n" +
                "  },\n" +
                "  series: [\n" +
                "    {\n" +
                "      name: 'Nightingale Chart',\n" +
                "      type: 'pie',\n" +
                "      radius: [30, 100],\n" +
                "      center: ['50%', '50%'],\n" +
                "      roseType: 'area',\n" +
                "      itemStyle: {\n" +
                "        borderRadius: 8\n" +
                "      },\n" +
                "      data: [\n" +
                "        { value: 40, name: 'rose 1' },\n" +
                "        { value: 38, name: 'rose 2' },\n" +
                "        { value: 32, name: 'rose 3' },\n" +
                "        { value: 30, name: 'rose 4' },\n" +
                "        { value: 28, name: 'rose 5' },\n" +
                "        { value: 26, name: 'rose 6' },\n" +
                "        { value: 22, name: 'rose 7' },\n" +
                "        { value: 18, name: 'rose 8' }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}"
    }

    private fun getData4(): String {
        return "{\"title\":{\"text\":\"Basic Graph\"},\"tooltip\":{},\"animationDurationUpdate\":1500,\"animationEasingUpdate\":\"quinticInOut\",\"series\":[{\"type\":\"graph\",\"layout\":\"none\",\"symbolSize\":50,\"roam\":true,\"label\":{\"show\":true},\"edgeSymbol\":[\"circle\",\"arrow\"],\"edgeSymbolSize\":[4,10],\"edgeLabel\":{\"fontSize\":20},\"data\":[{\"name\":\"Node 1\",\"x\":300,\"y\":300,\"itemStyle\":{\"color\":\"red\"},\"label\":{\"position\":\"top\"}},{\"name\":\"Node 2\",\"x\":350,\"y\":300,\"label\":{\"position\":\"top\"}},{\"name\":\"Node 3\",\"x\":400,\"y\":300,\"label\":{\"position\":\"top\"}},{\"name\":\"Node 4\",\"x\":450,\"y\":300,\"label\":{\"position\":\"top\"}},{\"name\":\"Node 5\",\"x\":310,\"y\":365,\"label\":{\"position\":\"top\"}},{\"name\":\"Node 6\",\"x\":365,\"y\":365,\"label\":{\"position\":\"top\"}},{\"name\":\"Node 7\",\"x\":410,\"y\":365,\"label\":{\"position\":\"top\"}},{\"name\":\"Node 8\",\"x\":450,\"y\":365,\"label\":{\"position\":\"top\"}}],\"links\":[{\"source\":0,\"target\":1,\"symbolSize\":[5,20],\"label\":{\"show\":true},\"lineStyle\":{\"width\":2,\"curveness\":0.5}},{\"source\":\"Node 1\",\"target\":\"Node 2\"},{\"source\":\"Node 2\",\"target\":\"Node 3\"},{\"source\":\"Node 2\",\"target\":\"Node 4\"},{\"source\":\"Node 1\",\"target\":\"Node 4\"},{\"source\":\"Node 1\",\"target\":\"Node 5\"},{\"source\":\"Node 5\",\"target\":\"Node 6\"},{\"source\":\"Node 5\",\"target\":\"Node 2\"},{\"source\":\"Node 6\",\"target\":\"Node 7\"},{\"source\":\"Node 2\",\"target\":\"Node 7\"},{\"source\":\"Node 7\",\"target\":\"Node 8\"},{\"source\":\"Node 8\",\"target\":\"Node 4\"}],\"lineStyle\":{\"opacity\":0.9,\"width\":2,\"curveness\":0}}]}"
    }
}
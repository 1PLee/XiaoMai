/**
 * Created by liyipeng on 2018/3/19.
 */

$(document).ready(function () {

    loadVIPGrade();
    loadVenueCapacity();
});



/*加载网站会员构成信息*/
function loadVIPGrade() {
    var vipInfo = new Array();

    $.ajax({

        type:"get",
        url:"/Manager/countVIPGrade",
        contentType: 'application/json;charset=utf-8',
        success:function (result) {
            vipInfo = result;
            loadVIPGradePie(vipInfo);

        },
        error:function () {
            alert("countVIPGrade failed!")
        }

    })

}

/*加载Pie图信息*/
function loadVIPGradePie(vipInfoJSON) {

    var vipCountChart = echarts.init(document.getElementById("vipGradePie"));

    var option = {
        title : {
            text: '网站会员统计',

            x:'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data: ['白银会员','黄金会员','钻石会员']
        },

        color: ["#4BC39B", "#DEE2A7", "#D9E5F2"],
        series : [
            {
                name: '会员组成',
                type: 'pie',
                radius : '55%',
                center: ['50%', '60%'],
                data:[
                    {value:vipInfoJSON["白银会员"], name:'白银会员'},
                    {value:vipInfoJSON["黄金会员"], name:'黄金会员'},
                    {value:vipInfoJSON["钻石会员"], name:'钻石会员'}

                ],
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };

    vipCountChart.setOption(option);

}


function loadVenueCapacity() {
    var capacityInfo = new Array();

    $.ajax({

        type:"get",
        url:"/Manager/countVenueByCapacity",
        contentType: 'application/json;charset=utf-8',
        success:function (result) {
            capacityInfo = result;
            loadVenueCapacityPie(capacityInfo);

        },
        error:function () {
            alert("countVenueByCapacity failed!")
        }

    })

}

function loadVenueCapacityPie(capacityInfo) {
    var venueCapacityChart = echarts.init(document.getElementById("venueCapacityPie"));

    var option = {
        title : {
            text: '网站场馆容纳能力统计',

            x:'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data: ['1000人以内','1000～2000人','2000～5000人','5000以上']
        },

        color: ["#81C0D5", "#AB75D1", "#94D98C", "#D7D98C"],
        series : [
            {
                name: '场馆容纳能力统计',
                type: 'pie',
                radius : '55%',
                center: ['50%', '60%'],
                data:[
                    {value:capacityInfo['1000以内'], name:'1000人以内'},
                    {value:capacityInfo['2000以内'], name:'1000~2000人'},
                    {value:capacityInfo['5000以内'], name:'2000~5000人'},
                    {value:capacityInfo['10000以内'], name:'5000人以上'}

                ],
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };

    venueCapacityChart.setOption(option);

}

/*查询网站财务状况*/

$(document).on(
    {
        click:function () {

            var queryYear = $('#queryYear').val();
            var totalIncomeList = new Array();
            var backMoneyList = new Array();
            var totalProfit = new Array();
            var performsList = new Array();
            alert(queryYear);

            $.ajax({
                type:"get",
                url:"/Manager/getFinancialInfo",
                contentType:'application/json;charset=utf-8',
                data:{"year": queryYear},
                success: function (result) {

                    for(var i =0;i<result.length;i++){
                        totalIncomeList.push(result[i]["totalIncome"]);
                        backMoneyList.push(result[i]["backMoney"]);
                        totalProfit.push(result[i]["totalProfit"]);
                        performsList.push(result[i]["performs"]);
                    }
                    loadFinancialChart(totalIncomeList, backMoneyList, totalProfit, performsList);

                },
                error:function () {
                    alert("getFinancialInfo failed!")
                }

            });


        }
    },'#queryBtn'

);

function loadFinancialChart(totalIncome,backMoney,totalProfit,performs) {
    var financialChart = echarts.init(document.getElementById("ticketFinancialInfo"));



    var option = {
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'cross',
                crossStyle: {
                    color: '#999'
                }
            }
        },
        toolbox: {
            feature: {
                dataView: {show: true, readOnly: false},
                magicType: {show: true, type: ['line', 'bar']},
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        legend: {
            data:['总收入','退款金额','利润','演出数量']
        },
        xAxis: [
            {
                type: 'category',
                data: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
                axisPointer: {
                    type: 'shadow'
                }
            }
        ],
        yAxis: [
            {
                type: 'value',
                name: '金额',
                min: 0,
                max: 11000,
                interval: 1000,
                axisLabel: {
                    formatter: '{value} 元'
                }
            },
            {
                type: 'value',
                name: '数量',
                min: 0,
                max: 200,
                interval: 10,
                axisLabel: {
                    formatter: '{value} 场'
                }
            }
        ],
        series: [
            {
                name:'总收入',
                type:'bar',
                data:totalIncome
            },
            {
                name: '退款金额',
                type: 'bar',
                data: backMoney
            },
            {
                name:'利润',
                type:'bar',
                data: totalProfit
            },
            {
                name:'演出数量',
                type:'line',
                yAxisIndex: 1,
                data:performs
            }
        ]
    };

    financialChart.setOption(option);

}




/**
 * Created by liyipeng on 2018/3/7.
 */


var orderMoneyCount ;/*已完成  未完成 退款  */


/*显示个人统计信息*/
$(document).on(
    {
        click:function () {

            allHide();
            $('#personCountDiv').show();

        }
    },'#seeConsume'

);


function loadUserConsumeInfo() {
    var userId = sessionStorage.getItem("userID");
    orderMoneyCount = new Array();

    $.ajax({
       type:"get",
        url:"/UserOrder/getUserOrderCount",
        contentType:'application/json;charset=utf-8',
        data:{"userId":userId},
        success:function (result) {
            orderMoneyCount = result.data;
            loadUserConsumeCharts();
        },
        error:function () {
            alert("getUserOrderCount failed!")
        }

    });


}

function loadUserConsumeCharts() {

    var consumeChart = echarts.init(document.getElementById("personCountDiv"));

    var option = {
        title : {
            text: '用户消费统计',

            x:'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data: ['已完成','未完成','退款']
        },

        color: ["#4BC39B", "#DEE2A7", "#D9E5F2"],
        series : [
            {
                name: '订单类型',
                type: 'pie',
                radius : '55%',
                center: ['50%', '60%'],
                data:[
                    {value:orderMoneyCount[0], name:'已完成'},
                    {value:orderMoneyCount[1], name:'未完成'},
                    {value:orderMoneyCount[2], name:'退款'}

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

    consumeChart.setOption(option);


}
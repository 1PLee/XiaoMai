/**
 * Created by liyipeng on 2018/3/18.
 */
var venueName;


$(document).ready(function () {
    venueName = sessionStorage.getItem("venueName");
    loadVenuePerform();

});


/*得到场馆举办的演出信息 state为1代表演出还处于售卖中*/
function loadVenuePerform() {
    var performArray = new Array();
    var performNames = new Array();
    var performIncome = new Array(); //每场演出卖出的总收入
    var performTickets = new Array(); //每场演出卖出的总票数

    $.ajax({
        type: "get",
        url: "/Venue/getVenueCountInfo",
        contentType: 'application/json;charset=utf-8',
        data: {"venue": venueName},
        success:function (result) {
            performArray = result;
            quickSort(performArray);
            for(var i=performArray.length-1;i>=0;i--){
                performNames.push(performArray[i]["name"]);
                performIncome.push(performArray[i]["performIncomeVO"]["totalIncome"].toFixed(1));
                performTickets.push(performArray[i]["performIncomeVO"]["totalTicketNum"]);

            }
            loadBookInfoChart(performNames,performIncome, performTickets);
        },
        error:function () {
            alert("getVenueCountInfo failed!");
        }


    });

}

/*得到预定信息统计图表*/
function loadBookInfoChart(performNames,performIncomes, performTickets) {
    var financialChart = echarts.init(document.getElementById("performBookChart"));

    var maxPerformIncome = parseFloat(performIncomes[0]) + 1000.0;

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
            data:['总收入','预定票数']
        },
        xAxis: [
            {
                type: 'category',
                data: performNames,
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
                max: maxPerformIncome,
                interval: 1000,
                axisLabel: {
                    formatter: '{value} 元'
                }
            },
            {
                type: 'value',
                name: '数量',
                min: 0,
                max: 20,
                interval: 1,
                axisLabel: {
                    formatter: '{value} 张'
                }
            }
        ],
        series: [
            {
                name:'总收入',
                type:'bar',
                data: performIncomes
            },

            {
                name:'卖出票数',
                type:'line',
                yAxisIndex: 1,
                data:performTickets
            }
        ]
    };

    financialChart.setOption(option);


}

/*利用快速排序得到预售款最多的前五个演出*/
function quickSort(performArray) {

    function sort(array, left, right) {

        if(left>right){
            return;
        }

        var pivot = array[left]["performIncomeVO"]["totalIncome"];
        var i = left;
        var j = right;

        while (i != j) {

            while (array[j]["performIncomeVO"]["totalIncome"] >= pivot && j>i ){ j--; }

            while (array[i]["performIncomeVO"]["totalIncome"] <= pivot && i<j){ i++; }

            if(i != j){
                swap(array, i, j);
            }

        }

        if(i != left){
            swap(array, i, left);
        }

        sort(array, left, i - 1);
        sort(array, i + 1, right);

        return;
    }

    function swap(array, i, j) {
        var tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;

    }

    var length = performArray.length;
    sort(performArray, 0, length-1);

}

/*查询场馆年收入*/
$(document).on(
    {
        click:function () {
            var year = $('#queryYear').val();


            $.ajax({
                type:'get',
                url:"/Venue/getVenueIncome",
                contentType:'application/json;charset=utf-8',
                data:{"venue": venueName, "year": year},
                success:function (result) {
                    if(result.queryResult == "FAILURE"){
                        alert("抱歉 该年场馆没有收入")
                    }else {
                        var income = result.income;
                        $('#yearLable').html(year + "年总收入:");
                        $('#incomeP').html(income);
                    }

                },
                error:function () {
                    alert("newPerform failed!")
                }

            })

        }
    },'#queryBtn'

);

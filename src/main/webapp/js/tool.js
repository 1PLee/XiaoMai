/**
 * Created by liyipeng on 2018/2/17.
 */

function getQueryString(key){//得到英文或中文的url参数
    var reg = new RegExp("(^|&)"+key+"=([^&]*)(&|$)");
    var result = window.location.search.substr(1).match(reg);
    return result?decodeURIComponent(result[2]):null;
}


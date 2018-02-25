/**
 * Created by liyipeng on 2018/2/17.
 */

function getQueryString(key){//得到英文或中文的url参数
    var reg = new RegExp("(^|&)"+key+"=([^&]*)(&|$)");
    var result = window.location.search.substr(1).match(reg);
    return result?decodeURIComponent(result[2]):null;
}



function showUser() {
    var username;
    var is_username;
    username=sessionStorage.getItem("userID");

    // alert(username);

    if(username != null)
    {
        //alert("jinrule");
        document.getElementById('userSpan').innerHTML = username;

        $("#noneUser").hide();
        $("#userDrop").show();
        is_username=true;
        //$("#userDropdown").css('display','block');

    }

    else
    {

        $("#userDrop").hide();
        $("#noneUser").show();

        //$("#noneuserDropdown").css('display','block');

        is_username=false;
    }

    return is_username;
}

/*注销*/
$(document).on(
    {
        click:function () {
            sessionStorage.clear();
            window.location.href = "http://localhost:9090/pages/welcome.html"

        }
    },'#cancel'

);
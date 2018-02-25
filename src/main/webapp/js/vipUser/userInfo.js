/**
 * Created by liyipeng on 2018/2/24.
 */


$(document).ready(function () {
    $('#userInfoDiv').hide();
    $('#changePasswordDiv').hide();
    $('#vipGradeFormDiv').hide();
   showUser();
});

/*查看个人信息*/
$(document).on(
    {
        click:function () {
            $('#changePasswordDiv').hide();
            $('#vipGradeFormDiv').hide();
            $('#userInfoDiv').show();

        }
    },'#userInfoLi'

);

/*点击会员等级展示*/
$(document).on(
    {
        click:function () {

            $('#userInfoDiv').hide();
            $('#changePasswordDiv').hide();
            $('#vipGradeFormDiv').show();

        }
    },'#vipGradeLi'

);

/*修改密码展示*/
$(document).on(
    {
        click:function () {
           $('#userInfoDiv').hide();
           $('#vipGradeFormDiv').hide();
           $('#changePasswordDiv').show();

        }
    },'#changePasswordLi'

);
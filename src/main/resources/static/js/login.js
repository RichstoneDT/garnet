/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

// TODO:暂时修改
// var baseURL = "http://localhost:8080/garnet/v1.0/";
// var baseURL = "http://192.168.0.200:12306/garnet/v1.0/";
// var baseURL = "http://192.168.111.100:12306/garnet/api/v1.0/";
// var baseURL = "http://localhost:12306/garnet/api/v1.0/";
var baseURL = "/garnet/api/v1.0/";
// var baseURL = "http://192.168.108.100:12306/garnet/api/v1.0/";

var nowTime = $.now();
var vm = new Vue({
    el: '#garnetApp',
    data: {
        userName: '',
        password: '',
        captcha: '',
        nowTime:'',
        error: false,
        errorMsg: '',
        src: baseURL + 'kaptcha?nowTime=' + nowTime,
        inputtext:{}
    },
    methods: {
        refreshCode: function () {
            var oldTime = nowTime;
            nowTime = $.now();
            this.src = baseURL + "kaptcha?nowTime=" + nowTime + "&oldTime=" + oldTime;
        },submit:function(){
            // alert(JSON.stringify(this.inputtext));
        },
        login: function () {

            var userId = localStorage.getItem("userId");

            if (vm.userName != null && vm.userName != "") {
                document.cookie = "userName=" + vm.userName + ";";
            }

            var data = {
                userName: vm.userName,
                password: CryptoJS.MD5(vm.password).toString(),
                kaptcha: vm.captcha,
                nowTime: nowTime,
                appCode: 'garnet'
            };
            $.ajax({
                type: "POST",
                url: baseURL + "users/garnetlogin",
                data: JSON.stringify(data),
                contentType: "application/json",
                dataType: "",
                success: function (result) {
                    if (result.loginStatus == "success") {
                        localStorage.setItem("refreshToken", result.refreshToken);
                        localStorage.setItem("accessToken", result.accessToken);
                        localStorage.setItem("userId", result.user.id);
                        localStorage.setItem("userName", result.user.userName);
                        localStorage.setItem("belongToGarnet", result.user.belongToGarnet)
                        localStorage.setItem("requestTime", new Date().getTime());

                        parent.location.href = 'index.html';
                    } else {
                        vm.error = true;
                        vm.errorMsg = result.message;
                        vm.refreshCode()
                    }
                },
                error: function(result){

                 vm.error = true;
                vm.errorMsg = result.responseJSON.messageDescription;
                vm.refreshCode()
                }

            });
        }
    }
});

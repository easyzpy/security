localStorage
$(function () {
    $("#login").click(function(){
        doLogin()
    })

    // #("btn").click(function () {
    //     var token = localStorage.getItem("token")
    //     console.log(token)
    // }
    $("#btn").click(function(){
        var token = localStorage.getItem("token")
        console.log(token)
    })
    $("#btn2").click(function(){
        $.ajax({
            url:"/list",
            type: "POST",
            headers : createAuthorizationTokenHeader(),
            success:function (data) {
                console.log(data)
            }
        })

    })
})
function createAuthorizationTokenHeader(){
    var token = getToken()
    if (token !== null) {
        return {"Authorization": token};
    } else {
        return {}
    }
}
function getToken(){
    return localStorage.getItem("token")
}
function doLogin() {
    var username = $("[name='username']").val();
    var password = $("[name='password']").val();
    $.ajax({
        url: "/auth",
        type: "POST",
        data: {username:username, password: password},
        success:function (data) {
            // console.log(typeof data)
            // console.log(data)
            if (typeof data === "string") {
                localStorage.setItem("token", data);
            }
        }
    })

}
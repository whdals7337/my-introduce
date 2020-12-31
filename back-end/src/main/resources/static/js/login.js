var login = {
    init: function() {
    var _this = this;
    $('#login-btn').on('click', function(){
    var form  = $('#myForm')[0];
    var data = new FormData(form);

    $.ajax({
            type: "POST",
            url: "/login/login",
            headers : { // ajax 해더 명시 필수
                        'X-Requested-With' : 'XMLHttpRequest'
                    },
            data: data,
            processData: false,
            contentType: false,
            cache: false,
            timeout: 600000,
            success: function (data) {
                if(data.is_access === "access"){
                    alert("로그인에 성공하였습니다.");
                    window.location.href="/";
                }
                else {
                    alert("계정정보가 올바르지 않습니다.");
                    $('#id').val("");
                    $('#password').val("");
                }

            },
            error: function (e) {
                 alert("계정정보가 올바르지 않습니다.");
                 $('#id').val("");
                 $('#password').val("");
            }
        });
    });
    }
};

login.init();
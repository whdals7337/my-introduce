var clickSubmit = function () {
    var form  = $('#myForm')[0];
    var data = new FormData(form);

    $.ajax({
        type: "POST",
        url: "/api/member",
        data: data,
        processData: false,
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function (data) {
            alert('등록완료')
            window.location.href="/member/memberList";
        },
        error: function (e) {
            alert('실패')
        }
    });
};

var clickUpdate = function (id) {
    var form  = $('#myForm')[0];
    var data = new FormData(form);

    $.ajax({
        type: "PUT",
        url: "/api/member/"+id,
        data: data,
        processData: false,
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function (data) {
            alert('수정')
            window.location.href="/member/memberList";
        },
        error: function (e) {
            alert('실패')
        }
    });
};


var clickDelete = function(id) {
    $.ajax({
        type: "DELETE",
        url: "/api/member/"+id,
        processData: false,
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function (data) {
            alert('삭제완료')
            window.location.href = "/member/memberList"
        },
        error: function (e) {
            alert('실패')
        }
    });

}

var clickSubmit = function () {
    var form  = $('#myForm')[0];
    var data = new FormData(form);

    $.ajax({
        type: "POST",
        url: "/api/project",
        headers : { // ajax 해더 명시 필수
                    'X-Requested-With' : 'XMLHttpRequest'
                },
        data: data,
        processData: false,
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function (data) {
            if(data.status === "200"){
                alert('등록완료')
                window.location.href="/project/projectList";
            }
            else {
                alert(data.msg)
            }
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
        url: "/api/project/"+id,
        headers : { // ajax 해더 명시 필수
                    'X-Requested-With' : 'XMLHttpRequest'
                },
        data: data,
        processData: false,
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function (data) {
            if(data.status === "200"){
                alert('수정완료')
                window.location.href="/project/projectList";
            }
            else {
                alert(data.msg)
            }
        },
        error: function (e) {
            alert('실패')
        }
    });
};


var clickDelete = function(id) {
    $.ajax({
        type: "DELETE",
        url: "/api/project/"+id,
        headers : { // ajax 해더 명시 필수
                    'X-Requested-With' : 'XMLHttpRequest'
                },
        processData: false,
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function (data) {
            if(data.status === "200"){
                alert('삭제완료')
                window.location.href="/project/projectList";
            }
            else {
                alert(data.msg)
            }
        },
        error: function (e) {
            alert('실패')
        }
    });

}

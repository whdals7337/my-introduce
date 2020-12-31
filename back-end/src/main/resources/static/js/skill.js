var clickSubmit = function () {
    var form  = $('#myForm')[0];
    var data = new FormData(form);

    $.ajax({
        type: "POST",
        url: "/api/skill",
        headers : { // ajax 해더 명시 필수
                    'X-Requested-With' : 'XMLHttpRequest'
                },
        data: data,
        processData: false,
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function (data) {
            alert('등록완료')
            window.location.href="/skill/skillList";
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
        url: "/api/skill/"+id,
        headers : { // ajax 해더 명시 필수
                    'X-Requested-With' : 'XMLHttpRequest'
                },
        data: data,
        processData: false,
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function (data) {
            alert('수정')
            window.location.href="/skill/skillList";
        },
        error: function (e) {
            alert('실패')
        }
    });
};


var clickDelete = function(id) {
    $.ajax({
        type: "DELETE",
        url: "/api/skill/"+id,
        headers : { // ajax 해더 명시 필수
                    'X-Requested-With' : 'XMLHttpRequest'
                },
        processData: false,
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function (data) {
            alert('삭제완료')
            window.location.href = "/skill/skillList"
        },
        error: function (e) {
            alert('실패')
        }
    });

}

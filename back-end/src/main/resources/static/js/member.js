var clickSubmit = function () {
    var form  = $('#myForm')[0];
    var data = new FormData(form);

    $.ajax({
        type: "POST",
        url: "/api/member",
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
        headers : { // ajax 해더 명시 필수
                    'X-Requested-With' : 'XMLHttpRequest'
                },
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


var clickSelect = function (id) {
    if(id == "Y"){
        console.log("이미 대표설정된 멤버입니다.");
        return false;
    }
    var data = new FormData();
    data.append("id", id);

    $.ajax({
        type: "POST",
        url: "/member/select",
        headers : { // ajax 해더 명시 필수
            'X-Requested-With' : 'XMLHttpRequest'
        },
        data: data,
        processData: false,
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function (data) {
            if(data.member_id){
                alert('선택완료')
                window.location.href="/member/memberList";
            }
        },
        error: function (e) {
            alert('대표설정에 실패하였습니다.')
        }
    });
};
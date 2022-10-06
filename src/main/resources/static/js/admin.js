let index = {
  init: function () {
    $("#btn-memDelete").on("click", () => {
      this.delete();
    });
  },

  delete: function () {
    let memberNo = $("#memberNo").val();
    console.log(memberNo);

    $.ajax({
      type: "DELETE",
      url: "/admin/deleteMem/" + memberNo,
      dataType: "json",
    })
      .done(function (resp) {
        alert("삭제되었습니다.");
        location.href = "/admin/memberManage";
      })
      .fail(function (error) {
        alert(JSON.stringify(error));
      });
  },
};

index.init();

function levelUpEvent(memberNo) {
  console.log(memberNo);

  $.ajax({
    type: "GET",
    url: "/admin/levelUp/" + memberNo,
    dataType: "json",
  })
    .done(function (resp) {
      alert("관리자 등급으로 수정되었습니다.");
      location.href = "/admin/memberManage";
    })
    .fail(function (error) {
      alert(JSON.stringify(error));
    });
}

function deleteMem(memberNo) {
  console.log(memberNo);

  $.ajax({
    type: "DELETE",
    url: "/admin/deleteMem/" + memberNo,
    dataType: "json",
  })
    .done(function (resp) {
      alert("삭제되었습니다.");
      location.href = "/admin/memberManage";
    })
    .fail(function (error) {
      alert(JSON.stringify(error));
    });
}

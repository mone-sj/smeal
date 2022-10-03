let index = {
  init: function () {
    $("#btn-memDelete").on("click", () => {
      this.delete();
    });
    $("#btn-levelUp").on("click", () => {
      this.levelUp();
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

  levelUp: function () {
    console.log("levelUp 함수에 들어옴");
    let memberNo = $("#memId").text();
    console.log(memberNo);

    // $.ajax({
    //   type: "GET",
    //   url: "/admin/levelUp/" + memberNo,
    //   //   data: JSON.stringify(data),
    //   //   contentType: "application/json; charset=utf-8",
    //   dataType: "json",
    // })
    //   .done(function (resp) {
    //     alert("관리자 등급으로 수정되었습니다.");
    //     location.href = "/admin/memberManage";
    //   })
    //   .fail(function (error) {
    //     alert(JSON.stringify(error));
    //   });
  },
};

index.init();

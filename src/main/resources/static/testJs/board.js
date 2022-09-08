let index = {
  init: function () {
    $("#btn-save").on("click", () => {
      this.save();
    });
    $("#btn-delete").on("click", () => {
      this.deleteById();
    });
    $("#btn-update").on("click", () => {
      this.update();
    });
    $("#btn-reply-save").on("click", () => {
      this.replySave();
    });
  },

  save: function () {
    let data = {
      title: $("#title").val(),
      content: $("#content").val(),
    };

    $.ajax({
      type: "POST",
      url: "/auth/board/save",
      data: JSON.stringify(data), // http body data
      contentType: "application/json; charset=utf-8",
      dataType: "json",
    })
      .done(function (resp) {
        alert("글쓰기가 완료되었습니다.");
        location.href = "/auth/board";
      })
      .fail(function (error) {
        // 응답 실패하면 수행
        alert(JSON.stringify(error));
      });
  },

  deleteById: function () {
    let id = $("#id").text();

    $.ajax({
      type: "DELETE",
      url: `/auth/board/delete/${id}`,
      dataType: "json",
    })
      .done(function (resp) {
        // 응답 성공하면 수행
        alert("삭제가 완료되었습니다.");
        location.href = "/auth/board";
      })
      .fail(function (error) {
        // 응답 실패하면 수행
        alert(JSON.stringify(error));
      });
  },

  update: function () {
    let id = $("#id").val();
    console.log(id);

    let data = {
      title: $("#title").val(),
      content: $("#content").val(),
    };

    let send_url = `/auth/board/update/${id}`;
    console.log(send_url);

    $.ajax({
      type: "PUT",
      url: send_url,
      data: JSON.stringify(data),
      contentType: "application/json;charset=utf-8",
      dataType: "json",
    })
      .done(function (res) {
        alert("글 수정이 완료되었습니다.");
        location.href = "/auth/board";
      })
      .fail(function (error) {
        alert(JSON.stringify(error));
      });
  },

  replySave: function () {
    let data = {
      userId: $("#userId").val(),
      boardId: $("#boardId").val(),
      content: $("#reply-content").val(),
    };
    console.log(data);

    $.ajax({
      type: "POST",
      url: `/auth/board/${data.boardId}/reply`,
      data: JSON.stringify(data), // http body data
      contentType: "application/json; charset=utf-8",
      dataType: "json",
    })
      .done(function (resp) {
        // 응답 성공하면 수행
        alert("댓글작성이 완료되었습니다.");
        location.href = `/auth/board/${data.boardId}`;
      })
      .fail(function (error) {
        // 응답 실패하면 수행
        alert(JSON.stringify(error));
      });
  },

  replyDelete: function (boardId, replyId) {

    $.ajax({
      type: "DELETE",
      url: `/auth/board/${boardId}/reply/${replyId}`,
      contentType: "application/json; charset=utf-8",
      dataType: "json",
    })
      .done(function (resp) {
        // 응답 성공하면 수행
        alert("댓글삭제가 완료되었습니다.");
        location.href = `/auth/board/${boardId}`;
      })
      .fail(function (error) {
        // 응답 실패하면 수행
        alert(JSON.stringify(error));
      });
  },
};

index.init();

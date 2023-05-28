function check(value, pattern, message, element) {
  if (value == "") {
    element.text("필수입력입니다").attr("class", "fail");
    return false;
  }
  if (pattern.test(value) == false) {
    element.text(message).attr("class", "fail");
    return false;
  }
  return true;
}

function nameCheck() {
  $("#name_msg").text("");
  const pattern = /^[가-힣]{2,10}$/;
  return check(
    $("#name").val(),
    pattern,
    "이름은 한글 2~10글자입니다",
    $("#name_msg")
  );
}
function resnameCheck() {
  $("#name_msg-res").text("");
  const pattern = /^[가-힣]{2,10}$/;
  return check(
    $("#name-res").val(),
    pattern,
    "이름은 한글 2~10글자입니다",
    $("#name_msg-res")
  );
}

function personalIdCheck() {
  $("#personalId_msg").text("");
  const pattern = /^(?:[0-9]{2}(?:0[1-9]|1[0-2])(?:0[1-9]|[1,2][0-9]|3[0,1]))$/;
  return check(
    $("#personalId").val(),
    pattern,
    "주민등록번호 형식에 맞게 입력해주세요",
    $("#personalId_msg")
  );
}
function respersonalIdCheck() {
  $("#personalId_msg-res").text("");
  const pattern = /^(?:[0-9]{2}(?:0[1-9]|1[0-2])(?:0[1-9]|[1,2][0-9]|3[0,1]))$/;
  return check(
    $("#personalId-res").val(),
    pattern,
    "주민등록번호 형식에 맞게 입력해주세요",
    $("#personalId_msg-res")
  );
}

function personalId2Check() {
  $("#personalId_msg").text("");
  const pattern = /^[1-8][0-9]{6}$/;
  return check(
    $("#personalId2").val(),
    pattern,
    "주민등록번호 형식에 맞게 입력해주세요",
    $("#personalId_msg")
  );
}
function respersonalId2Check() {
  $("#personalId_msg-res").text("");
  const pattern = /^[1-8][0-9]{6}$/;
  return check(
    $("#personalId2-res").val(),
    pattern,
    "주민등록번호 형식에 맞게 입력해주세요",
    $("#personalId_msg-res")
  );
}

function emailCheck() {
  $("email_msg").text("");
  const pattern =
    /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
  return check(
    $("#email").val(),
    pattern,
    "이메일을 정확하게 입력하세요",
    $("#email_msg")
  );
}
function resemailCheck() {
  $("email_msg-res").text("");
  const pattern =
    /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
  return check(
    $("#email-res").val(),
    pattern,
    "이메일을 정확하게 입력하세요",
    $("#email_msg-res")
  );
}

function telCheck() {
  $("tel_msg").text("");
  const pattern = /^01([0|1|6|7|8|9])(?:[0-9]{4})(?:[0-9]{4})$/;
  return check(
    $("#tel").val(),
    pattern,
    "휴대전화 형식에 맞게 입력하세요",
    $("#tel_msg")
  );
}
function restelCheck() {
  $("tel_msg-res").text("");
  const pattern = /^01([0|1|6|7|8|9])(?:[0-9]{4})(?:[0-9]{4})$/;
  return check(
    $("#tel-res").val(),
    pattern,
    "휴대전화 형식에 맞게 입력하세요",
    $("#tel_msg-res")
  );
}

function usernameCheck() {
  const value = $("#username").val();
  $("#username").val(value);
  return check(
    value,
    /^[a-z0-9]{7,10}$/,
    "아이디는 문자, 숫자의 조합 7~10자입니다",
    $("#username_msg")
  );
}
function resusernameCheck() {
  const value = $("#username-res").val();
  $("#username-res").val(value);
  return check(
    value,
    /^[a-z0-9]{7,10}$/,
    "아이디는 문자, 숫자의 조합 7~10자입니다",
    $("#username_msg-res")
  );
}

function passwordCheck() {
  $("#password_msg").text("");
  const pattern =
    /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/;
  return check(
    $("#password").val(),
    pattern,
    "비밀번호는 문자, 숫자, 특수문자의 조합 최소 8자리입니다.",
    $("#password_msg")
  );
}
function respasswordCheck() {
  $("#password_msg-res").text("");
  const pattern =
    /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/;
  return check(
    $("#password-res").val(),
    pattern,
    "비밀번호는 문자, 숫자, 특수문자의 조합 최소 8자리입니다.",
    $("#password_msg-res")
  );
}

function password2Check() {
  $("#password2_msg").text("");
  const value = $("#password2").val();
  if (value == "") {
    $("#password2_msg").text("필수입력입니다").attr("class", "fail");
    return false;
  }
  if (value != $("#password").val()) {
    $("#password2_msg")
      .text("비밀번호가 일치하지 않습니다.")
      .attr("class", "fail");
    return false;
  }
}
function respassword2Check() {
  $("#password2_msg-res").text("");
  const value = $("#password2-res").val();
  if (value == "") {
    $("#password2_msg-res").text("필수입력입니다").attr("class", "fail");
    return false;
  }
  if (value != $("#password-res").val()) {
    $("#password2_msg-res")
      .text("비밀번호가 일치하지 않습니다.")
      .attr("class", "fail");
    return false;
  }
}

function nowpasswordCheck() {
  $("#nowpassword_msg").text("");
  const value = $("#nowpassword").val();
  if (value == "") {
    $("#nowpassword_msg").text("필수입력입니다").attr("class", "fail");
    return false;
  }
}
function resnowpasswordCheck() {
  $("#nowpassword_msg-res").text("");
  const value = $("#nowpassword-res").val();
  if (value == "") {
    $("#nowpassword_msg-res").text("필수입력입니다").attr("class", "fail");
    return false;
  }
}

function newpasswordCheck() {
  $("#newpassword_msg").text("");
  const value = $("#newpassword").val();
  if (value == "") {
    $("#newpassword_msg").text("필수입력입니다").attr("class", "fail");
    return false;
  }
  if (value == $("#nowpassword").val()) {
    $("#newpassword_msg")
      .text("현재 비밀번호와 동일한 비밀번호는 사용할 수 없습니다.")
      .attr("class", "fail");
    return false;
  }
  const pattern =
    /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/;
  return check(
    $("#newpassword").val(),
    pattern,
    "비밀번호는 문자, 숫자, 특수문자의 조합 최소 8자리입니다.",
    $("#newpassword_msg")
  );
}
function resnewpasswordCheck() {
  $("#newpassword_msg-res").text("");
  const value = $("#newpassword-res").val();
  if (value == "") {
    $("#newpassword_msg-res").text("필수입력입니다").attr("class", "fail");
    return false;
  }
  if (value == $("#nowpassword-res").val()) {
    $("#newpassword_msg-res")
      .text("현재 비밀번호와 동일한 비밀번호는 사용할 수 없습니다.")
      .attr("class", "fail");
    return false;
  }
  const pattern =
    /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/;
  return check(
    $("#newpassword-res").val(),
    pattern,
    "비밀번호는 문자, 숫자, 특수문자의 조합 최소 8자리입니다.",
    $("#newpassword_msg-res")
  );
}

function newpassword2Check() {
  $("#newpassword2_msg").text("");
  const value = $("#newpassword2").val();
  if (value == "") {
    $("#newpassword2_msg").text("필수입력입니다").attr("class", "fail");
    return false;
  }
  if (value != $("#newpassword").val()) {
    $("#newpassword2_msg")
      .text("비밀번호가 일치하지 않습니다.")
      .attr("class", "fail");
    return false;
  }
  const pattern =
    /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/;
  return check(
    $("#newpassword").val(),
    pattern,
    "비밀번호는 문자, 숫자, 특수문자의 조합 최소 8자리입니다.",
    $("#newpassword_msg")
  );
}
function resnewpassword2Check() {
  $("#newpassword2_msg-res").text("");
  const value = $("#newpassword2-res").val();
  if (value == "") {
    $("#newpassword2_msg-res").text("필수입력입니다").attr("class", "fail");
    return false;
  }
  if (value != $("#newpassword-res").val()) {
    $("#newpassword2_msg-res")
      .text("비밀번호가 일치하지 않습니다.")
      .attr("class", "fail");
    return false;
  }
  const pattern =
    /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/;
  return check(
    $("#newpassword-res").val(),
    pattern,
    "비밀번호는 문자, 숫자, 특수문자의 조합 최소 8자리입니다.",
    $("#newpassword_msg-res")
  );
}

$(document).ready(function () {
  const token = $("#token").val();
  $("#name").blur(nameCheck);
  $("#personalId").blur(personalIdCheck);
  $("#personalId2").blur(personalId2Check);
  $("#email").blur(async function () {
    if (emailCheck() == false) {
      return false;
    }
    try {
      await $.ajax("/hotel/member/check/email?email=" + $("#email").val());
      $("#email_msg").text("사용가능한 이메일입니다.").attr("class", "success");
    } catch (err) {
      $("#email_msg").text("사용중인 이메일입니다.").attr("class", "fail");
    }
  });
  $("#tel").blur(telCheck);
  $("#username").blur(async function () {
    if (usernameCheck() == false) {
      return false;
    }
    try {
      await $.ajax(
        "/hotel/member/check/username?username=" + $("#username").val()
      );
      $("#username_msg")
        .text("사용가능한아이디입니다.")
        .attr("class", "success");
    } catch (err) {
      $("#username_msg").text("이미사용중입니다.").attr("class", "fail");
    }
  });
  $("#password").blur(passwordCheck);
  $("#password2").blur(password2Check);
  $("#join").click(async function () {
    const result =
      nameCheck() &&
      personalIdCheck() &&
      personalId2Check() &&
      emailCheck() &&
      telCheck() &&
      usernameCheck() &&
      passwordCheck() &&
      password2Check();
    if (result == false) {
      alert("입력사항을 다시한번 확인해주세요.");
      return false;
    }
    try {
      await $.ajax("/hotel/member/check/email?email=" + $("#email").val());
      await $.ajax(
        "/hotel/member/check/username?username=" + $("#username").val()
      );
      $("#join_form").submit();
    } catch (err) {
      console.log(err);
      alert("아이디나 이메일이 사용 중입니다");
    }
  });
  $("#name-res").blur(resnameCheck);
  $("#personalId-res").blur(respersonalIdCheck);
  $("#personalId2-res").blur(respersonalId2Check);
  $("#email-res").blur(async function () {
    if (resemailCheck() == false) {
      return false;
    }
    try {
      await $.ajax("/hotel/member/check/email?email=" + $("#email-res").val());
      $("#email_msg-res")
        .text("사용가능한 이메일입니다.")
        .attr("class", "success");
    } catch (err) {
      $("#email_msg-res").text("사용중인 이메일입니다.").attr("class", "fail");
    }
  });
  $("#tel-res").blur(restelCheck);
  $("#username-res").blur(async function () {
    if (resusernameCheck() == false) {
      return false;
    }
    try {
      await $.ajax(
        "/hotel/member/check/username?username=" + $("#username-res").val()
      );
      $("#username_msg-res")
        .text("사용가능한아이디입니다.")
        .attr("class", "success");
    } catch (err) {
      $("#username_msg-res").text("이미사용중입니다.").attr("class", "fail");
    }
  });
  $("#password-res").blur(respasswordCheck);
  $("#password2-res").blur(respassword2Check);
  $("#join-res").click(async function () {
    const resresult =
      resnameCheck() &&
      respersonalIdCheck() &&
      respersonalId2Check() &&
      resemailCheck() &&
      restelCheck() &&
      resusernameCheck() &&
      respasswordCheck() &&
      respassword2Check();
    if (resresult == false) {
      alert("입력사항을 다시한번 확인해주세요.");
      return false;
    }
    try {
      await $.ajax("/hotel/member/check/email?email=" + $("#email-res").val());
      await $.ajax(
        "/hotel/member/check/username?username=" + $("#username-res").val()
      );
      $("#join_form-res").submit();
    } catch (err) {
      console.log(err);
      alert("아이디나 이메일이 사용 중입니다");
    }
  });

  $("#nowpassword").blur(nowpasswordCheck);
  $("#newpassword").blur(newpasswordCheck);
  $("#newpassword2").blur(newpassword2Check);
  $("#changepasswordbtn").click(async function () {
    const nowpassword = $("#nowpassword").val();
    const changeresult = newpasswordCheck() && newpassword2Check();
    try {
      const check = await $.ajax({
        url: "/hotel/member/checkNowPassword?nowpassword=" + nowpassword,
        method: "post",
        data: {
          _csrf: $("#token").val(),
        },
      });
      if (check == true) {
        if (changeresult == true) {
          alert("비밀번호가 변경 되었습니다.");
          $("#change_password").submit();
        }
      } else if (check == false) {
        alert("현재비밀번호를 다시 입력해주세요.");
      }
    } catch (err) {
      alert("사용자를 찾지 못했습니다.");
    }
  });

  $("#nowpassword-res").blur(resnowpasswordCheck);
  $("#newpassword-res").blur(resnewpasswordCheck);
  $("#newpassword2-res").blur(resnewpassword2Check);
  $("#changepasswordbtn-res").click(async function () {
    const resnowpassword = $("#nowpassword-res").val();
    const reschangeresult = resnewpasswordCheck() && resnewpassword2Check();
    try {
      const check = await $.ajax({
        url: "/hotel/member/checkNowPassword?nowpassword=" + resnowpassword,
        method: "post",
      });
      if (check == true) {
        if (reschangeresult == true) {
          alert("비밀번호가 변경 되었습니다.");
          $("#change_password-res").submit();
        }
      } else if (check == false) {
        alert("현재비밀번호를 다시 입력해주세요.");
      }
    } catch (err) {
      alert("사용자를 찾지 못했습니다.");
    }
  });
});

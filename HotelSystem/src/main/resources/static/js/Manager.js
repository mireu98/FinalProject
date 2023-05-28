//예약 검색에서 값을 찾지 못했을때
function notFoundBookList(err) {
  const bookListTable = $("#bookListTable");
  bookListTable.empty();
  //검색결과가 없을시 list의 길이를 조건으로 준다.
  const tableContent = `
            <tr>
              <td rowspan="12">${err.responseText}</td>
              <td></td>
              <td></td>
              <td></td>
              <td></td>
              <td></td>
              <td></td>
              <td></td>
              <td></td>
              <td></td>
              <td></td>
              <td></td>
            </tr>
            `;
  bookListTable.append(tableContent);
}

//팝업오픈
function openPopup(url) {
  //url을 받아서 그 값으로 새로운 html을 열어준다.
  window.open(
    url,
    "memberDetail",
    "width=800, height=700, scrollbars=1, toolbar=1, menubar=yes, left=300px, top=100px" //창의 크기 설정
  );
}

//bookList를 출력해주는 코드
function searchData(list) {
  const bookListTable = $("#bookListTable");
  bookListTable.empty();
  //검색결과가 있을시 html에 추가하는곳
  for (const l of list) {
    let breakfastContent = ``;
    let resNoContent = ``;
    let buttonContent = ``;
    let roomNoContent = ``;

    l.roomNo == null ? (roomNoContent = ``) : (roomNoContent = `${l.roomNo}`);

    l.breakfast != 0
      ? (breakfastContent = `<input type="checkbox" checked></input>`)
      : (breakfastContent = `<input type="checkbox"></input>`);

    l.resNo != null
      ? (resNoContent = `<input type="checkbox" checked></input>`)
      : (resNoContent = `<input type="checkbox"></input>`);

    // 버튼 생성시 오늘 날짜가 아니면 체크인이 안되게끔 만드는 코드
    if (l.bookStatus == "체크인대기") {
      const today = new Date();
      const year = today.getFullYear().toString();
      const month = (today.getMonth() + 1).toString().padStart(2, "0");
      const day = today.getDate().toString().padStart(2, "0");
      const formattedDate = `${year}-${month}-${day}`;
      if (l.bookDate == formattedDate) {
        buttonContent = `
         <span class="dropdown">
            <button type="button" class="btn btn-primary dropdown-toggle checkInBtn" data-bs-toggle="dropdown">체크인</button>
            <ul class="dropdown-menu checkInDropDown">
              <li></li>
            </ul>
         </span>
         <button type="button" class="btn btn-primary bookCancel">예약취소 <input type="hidden" value="${l.bookTel}"></button>
          `;
      } else {
        buttonContent = `
        <button type="button" class="btn btn-primary dropdown-toggle checkInBtn" data-bs-toggle="dropdown" disabled>체크인</button>
           <ul class="dropdown-menu checkInDropDown">
              <li></li>
           </ul>
          </span>
          <button type="button" class="btn btn-primary bookCancel">예약취소 <input type="hidden" value="${l.bookTel}"></button>
        `;
      }
    } else if (l.bookStatus == "체크인완료") {
      buttonContent = `
        <button type="button" class="btn btn-danger checkOutBtn">체크아웃 <input type="hidden" value="${l.bookTel}"></button>
        <button type="button" class="btn btn-success changeBook">예약변경 <input type="hidden" value="${l.bookTel}"></button>
      `;
    } else if (l.bookStatus == "체크아웃완료") {
      buttonContent = ``;
    } else if (l.bookStatus == "예약취소") {
      buttonContent = ``;
    }

    const tableContent = `
    <tr>
    <td><a href="#" onclick="openPopup('/hotel/manager/memberDetail?name=${l.booker}&tel=${l.bookTel}')">${l.booker}</a></td>
    <td>${l.bookTel}</td>
    <td>${l.totalCount}</td>
    <td>${roomNoContent}</td>
    <td>${l.bookDate}</td>
    <td>${l.bookRoomGrade}</td>
    <td>${l.checkIn}</td>
    <td>${l.checkOut}</td>
    <td>${breakfastContent}</td>
    <td>${resNoContent}</td>
    <td>${l.bookStatus}</td>
    <td>${buttonContent}</td>
      `;
    bookListTable.append(tableContent);
  }
}

//리스트 생성시 리스트 길이에따라 높이 변경
function heightController(list) {
  //list의 크기에따라 article의 높이를 조정
  var listAreaHeight = $("#listarea").height();
  var newPageHeight = listAreaHeight + list.length * 46;
  $("#listarea").height(newPageHeight);
}

//search버튼 클릭스 검색 조건들 체크해주는 메소드
function searchConditionCheck(token) {
  return (param = {
    isStay: $("#stayCheckBox").is(":checked"),
    isRestaurant: $("#restaurantCheckBox").is(":checked"),
    fromDate: $("#managerFrom").val(),
    toDate: $("#managerTo").val(),
    todayCheckBox: $("#todayCheckBox").is(":checked"),
    roomNum: Number($("#searchRoomNumber").val()),
    name: $("#searchName").val(),
    listType: $(".dropdown-menu a.active").data("index"),
    _csrf: token,
  });
}

const intervalId = setInterval(async function () {
  const token = $("#token").val();
  const targetTime = new Date();
  targetTime.setHours(11);
  targetTime.setMinutes(00);

  if (new Date() == targetTime) {
    clearInterval(intervalId);
    try {
      const list = await $.ajax({
        url: "/hotel/manager/alarm?_csrf=" + token,
        method: "post",
      });
      alert(JSON.stringify(list));
    } catch (err) {
      alert(err.responseText);
    }
  }
}, 1000);

$(document).ready(function () {
  const token = $("#token").val();

  //검색버튼 클릭시 이벤트
  $(document).on("click", "#searchBtn", async function () {
    //검색시 조건들을 param 변수를 담는다
    const param = searchConditionCheck(token);
    //ajax data 형식으로 param을 전달한다.
    try {
      const list = await $.ajax({
        url: "/hotel/manager/bookSearch",
        method: "post",
        data: param,
      });
      //동적으로 생성된 데이터를 테이블 포맷으로 맞춰준다.
      searchData(list);
      //동적으로 생성된 테이블 갯수에 따라 높이를 변경해준다.
      heightController(list);
    } catch (err) {
      notFoundBookList(err);
    }
  });

  //검색 토글 변경시 이벤트처리
  $(document).on("click", ".searchToggle a", async function () {
    //전에 active되어있던 토글의 active를 제거해준다
    $(".dropdown-menu a").removeClass("active");
    //선택된 this를 active 해준다
    $(this).addClass("active");
    //해당 this에 있는 text를 btn text에 넣어준다.
    let text = $(this).text();
    $("#dropdownBtn").text(text);

    //검색 조건 체크이후 ajax를 보내준다.

    const param = searchConditionCheck(token);

    try {
      const list = await $.ajax({
        url: "/hotel/manager/bookSearch",
        method: "post",
        data: param,
      });
      searchData(list);
      heightController(list);
    } catch (err) {
      notFoundBookList(err);
    }
  });

  //체크인버튼 클릭시 이벤트
  $(document).on("click", ".checkInBtn", async function () {
    //해당줄에 있는 roomGrade를 찾아온다(this를 기준으로 tr을 찾고 6번째 자식의 text)
    const roomGrade = $(this).closest("tr").find("td:nth-child(6)").text();
    try {
      //url로 방등급을 보낸다.
      const roomList = await $.ajax({
        url:
          "/hotel/manager/checkBtn?roomGrade=" + roomGrade + "&_csrf=" + token,
        method: "post",
      });
      const checkInDropDown = $(".checkInDropDown > li");
      //기존의 li를 비워주고
      checkInDropDown.empty();
      //해당 roomgrade에 맞는 방의 정보들을 룸번호와 상태를 나열해준다
      for (const r of roomList) {
        const dropdownMenu = `
        <li><a class="dropdown-item" href="#">${r.roomNo}(${r.roomStatus})</a></li>
          `;
        checkInDropDown.append(dropdownMenu);
      }
    } catch (err) {}
  });

  //나열된 방을 클릭했을때의 이벤트
  $(document).on("click", ".checkInDropDown a", async function (event) {
    //방의 text를 받아와서 ()를 잘라준다
    const clickedMenuText = $(this).text();
    const [roomNo, roomStatus] = clickedMenuText.split("(");
    const parsedRoomStatus = roomStatus.replace(")", "");
    //방배정에 필요한 예약자 이름을 찾아온다.
    const bookerName = $("td:nth-child(2)", $(this).closest("tr")).text();

    //방배정은 룸의 상태가 비어있음일시 배정을 할수있다.
    if (parsedRoomStatus == "비어있음") {
      try {
        const searchCondition = await $.ajax({
          //url로 roomNo와 bookerName을 보낸다.
          url:
            "/hotel/manager/checkIn?roomNo=" +
            roomNo +
            "&name=" +
            bookerName +
            "&_csrf=" +
            token,
          method: "post",
        });
        alert("방이 배정되었습니다.");
        location.href = "/hotel/manager/bookList";
      } catch (err) {
        console.log(err);
      }
      //비어있음이 아닐시
    } else {
      alert("이미 배정이 완료된 방입니다.");
    }
  });

  //예약취소
  $(document).on("click", ".bookCancel", function () {
    //예약자를 input hidden으로 숨겨두고 그값을 찾아온다.
    let bookno = parseInt($(this).find("input").val());
    $.ajax({
      url: "/pay/cancel_do",
      data: { bookno, _csrf: token },
      method: "post",
      statusCode: {
        200: function () {
          alert("예약 취소가 완료되었습니다.");
          location.reload();
        },
      },
    });
  });

  //체크아웃취소
  $(document).on("click", ".checkOutBtn", async function () {
    //hidden으로 숨겨둔 input에 예약자명을 숨겨두고 그 값을 찾아온다.
    const booker = $(this).find("input").val();
    //해당줄에 있는 roomNo를 찾아온다.
    const roomNo = $("td:nth-child(4)", $(this).closest("tr")).text();
    try {
      //url로 예약자와 방번호를 보낸다.
      const searchCondition = await $.ajax({
        url:
          "/hotel/manager/checkOut?bookTel=" +
          booker +
          "&roomNo=" +
          roomNo +
          "&_csrf=" +
          token,
        method: "post",
      });
      alert("체크아웃이 완료되었습니다");
      location.href = "/hotel/manager/bookList";
    } catch (err) {
      console.log(err);
    }
  });

  //예약변경
  $(document).on("click", ".changeBook", async function () {
    const row = $(this).closest("tr");
    var booktel = $(this).children("input").val();
    const breakfastChecked = row
      .find('td:nth-child(9) input[type="checkbox"]')
      .prop("checked");
    const resNoChecked = row
      .find('td:nth-child(10) input[type="checkbox"]')
      .prop("checked");

    const param = {
      breakfast: breakfastChecked,
      dinner: resNoChecked,
      bookTel: booktel,
      _csrf: token,
    };
    try {
      const searchCondition = await $.ajax({
        url: "/hotel/manager/changeBook",
        method: "post",
        data: param,
      });
      alert("예약이 변경되었습니다.");
      location.href = "/hotel/manager/bookList";
    } catch (err) {
      alert(err.responseText);
      location.href = "/hotel/manager/bookList";
    }
  });

  //엔터 클릭시 검색
  $(document).on("keyup", function (event) {
    // 엔터키가 눌리면
    if (event.keyCode === 13) {
      // 검색 click이벤트 발생시키기
      $("#searchBtn").click();
    }
  });

  //검색설정 초기화
  $(document).on("click", "#initBtn", function () {
    //초기상태로 전부 변경해준다.
    $("#stayCheckBox").prop("checked", false);
    $("#restaurantCheckBox").prop("checked", false);
    $("#todayCheckBox").prop("checked", true);
    $("#managerFrom").val("");
    $("#managerTo").val("");
    $("#searchRoomNumber").val("");
    $("#searchName").val("");
    $(".dropdown-item.active").removeClass("active");
    $(".dropdown-item[data-index='1']").addClass("active");
    $("#dropdownBtn").text($(".dropdown-item[data-index='1']").text());
    $("#managerFrom").attr("disabled", true);
    $("#managerTo").attr("disabled", true);
  });

  //todayCheck박스 이벤트
  $(document).on("change", "#todayCheckBox", function () {
    $("#todayCheckBox").is(":checked") == false
      ? $("#managerFrom").attr("disabled", false)
      : $("#managerFrom").attr("disabled", true);
  });

  //memberDetail 코드
  //blackList설정
  $(document).on("click", "#blackBtn", async function () {
    //예약자 정보 받아오기
    var booker = $(this).find("input").val();
    try {
      const blackBtn = await $.ajax({
        //url로 예약자 전송
        url: "/hotel/manager/blackBtn?name=" + booker + "&_csrf=" + token,
        method: "post",
      });
      alert(blackBtn);
      location.reload();
    } catch (err) {}
  });

  //vip설정
  $(document).on("click", "#vipBtn", async function () {
    //예약자 받기
    var booker = $(this).find("input").val();
    try {
      //url로 전송
      const blackBtn = await $.ajax({
        url: "/hotel/manager/vipBtn?name=" + booker + "&_csrf=" + token,
        method: "post",
      });
      alert("변경이 완료되었습니다");
      location.reload();
    } catch (err) {}
  });

  //아이디 활성화 버튼
  $(document).on("click", "#ableBtn", async function () {
    //예약자 받기
    var booker = $(this).find("input").val();
    try {
      //url전송
      const blackBtn = await $.ajax({
        url: "/hotel/manager/ableBtn?name=" + booker + "&_csrf=" + token,
        method: "post",
      });
      alert("변경이 완료되었습니다");
      location.reload();
    } catch (err) {}
  });

  //데이터 피커
  const today = new Date();
  let dateFormat = "mm/dd/yy",
    from = $("#managerFrom")
      .datepicker({
        defaultDate: null,
        changeMonth: true,
        numberOfMonths: 1,
      })
      .on("change", function () {
        const selectedDate = getDate(this);
        to.datepicker("option", "minDate", selectedDate);
        const $to = $("#managerTo");
        $to.prop("disabled", false);
      }),
    to = $("#managerTo")
      .datepicker({
        defaultDate: null,
        changeMonth: true,
        numberOfMonths: 1,
      })
      .on("change", function () {
        const selectedDate = getDate(this);
        from.datepicker("option", "maxDate", selectedDate);
      });

  function getDate(element) {
    let date;
    try {
      date = $.datepicker.parseDate(dateFormat, element.value);
    } catch (error) {
      date = null;
    }
    return date;
  }
});

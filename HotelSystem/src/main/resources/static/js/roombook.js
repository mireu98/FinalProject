function printtotalprice(gradeName, gradeprice) {
  let priceperpercent = 0;
  let totalprice = parseInt(gradeprice);
  if ($("#bfcheckbox").is(":checked")) {
    totalprice += parseInt($("#breakfastprice").text());

  }
  if ($("#dicheckbox").is(":checked")) {
    totalprice += 150000;
  }

  const cntday = parseInt($(".night").text());
  const paytotalprice = totalprice * cntday;
  const mylevel = $('#boookmylevel').text();
  parseInt(paytotalprice);
  if (mylevel == 'GOLD'){
    priceperpercent = paytotalprice-((paytotalprice/100)*10);
  }
  if (mylevel=='SILVER'){
    priceperpercent = paytotalprice-((paytotalprice/100)*5);
  }
  if(mylevel=='BRONZE'){
    priceperpercent = paytotalprice;
  }
  $("#roominfo").empty();
  $("#roomname").empty();
  $("#roominfo").append(priceperpercent);
  $("#roomname").append(gradeName);
}
function printRoomList($roomlist, $footer) {
  const html2 = `<div id = "inputdiv">
							<span>내정보 입력</span>  								
							<input type="checkbox" id = "myinfobox" checked>
							<span>인원수</span>
							<input type = "number" id = "totalcnt" max="4" min="1" value = "1">
							<input type="checkbox" id = "bfcheckbox">
							<span>조식여부(50,000)</span>
							<input type="checkbox" id = "dicheckbox">
							<span>석식여부(150,000)</span>
							<span>예약자 :</span>
							<input type="text" id = "booker">
							<span>예약자 연락처 :</span>
							<input type="text" id = "booktel">
				  </div><hr>`;
  $footer.append(html2);
  let index = 1;
  for (const b of $roomlist) {
    const html = `
	<div id = "roomlist">
		<input type="hidden" value="${b.gradeName}" id = "gradename">
		<div id = "roomlistimg">
		<img src = "/img/방이미지${index}.jpg" id = "roomimg"></div>
		<div id = "roomlistinfo"><span>${b.gradeName}</span>
		<span id = "breakfastprice" style="display: none;" >${b.breakfastprice}</span>
		<span id = "gradeprice">${b.gradeprice}</span></div>
		<div id = "roomlistbtn"><img id = "booklistbtn" src = "/img/선택버튼.png"></div>
		<br>
	</div><hr>`;
    if (b.reservableOrNot) {
      $footer.append(html);
    }
    index++;
  }
}

function kakaojs(result) {
  $.ajax({
    type: "get",
    url: "/pay/start",
    data: {
      item_name: `${result.gradename}`, //상품명
      quantity: "1", // 나는 고정
      total_amount: `${result.totalprice}`, //받아와야 하고
      tax_free_amount: "0", //필수라 넣어야됨
    },
    success: function (res) {
      location.href = res.next_redirect_pc_url;
    },
    error: function (error) {
      alert(error);
    },
  });
}
function printManagerRoomList(roomlist) {
  let index = 1;
  const roomlistarea = $("#managerroomlistdiv");
  roomlistarea.empty();
  for (const b of roomlist) {
    const html = `<div id = "roomlist">
		<input type="hidden" value="${b.gradeName}" id = "gradename">
		<div id = "roomlistimg">
		<img src = "/img/방이미지${index}.jpg" id = "roomimg"></div>
		<div id = "roomlistinfo"><span>${b.gradeName}</span>
		<span id = "breakfastprice" style="display: none;" >${b.breakfastprice}</span>
		<span id = "gradeprice">${b.gradeprice}</span></div>
		<div id = "roomlistbtn"><img id = "booklistbtn" src = "/img/선택버튼.png"></div>
		<br>
	</div><hr>`;
    roomlistarea.append(html);
    index++;
  }
}

function printmyroombook(list) {
  $("#mybookpagetbody").empty();
  const tbody = $("#mybookpagetbody");
  for (l of list) {
    const html = `
		<tr>
			<td>${l.checkin}</td>
			<td>${l.checkout}</td>
			<td>${l.booker}</td>
			<td>${l.booktel}</td>
			<td>${l.bookroomgrade}</td>
			<td><input type="hidden" value="${l.bookno}">
				<button class ="btn btn-danger" id = "deletebookbtn">예약취소</button>
			</td>
		</tr>
	`;
    tbody.append(html);
  }
}


//----------------------------------------------------------------------------------------------------------
$(document).ready(async function () {
  const tokken = $("#booktokken").val();
  $("#serchmybook").click(async function () {
    const from = $("#mypagepickdatestart").val();
    const to = $("#mypagepickdateend").val();
    try {
      const list = await $.ajax({
        url: "/hotel/client/findmybook",
        method: "post",
        data: {
          from,
          to,
          _csrf: tokken,
        },
      });
      printmyroombook(list);
      console.log(list);
    } catch (err) {
      alert(err);
    }
  });
  $(document).on("click", "#deletebookbtn", function () {
  const val =   confirm("예약을 취소하겟습니까?");
  if (val==true){


    const bookno = $(this).prev().val();
    $.ajax({
      url: "/pay/cancel_do",
      data: { bookno, _csrf: tokken },
      method: "post",
      statusCode: {
        200: function () {
          location.reload();
          alert("취소되었습니다");
        },
      },
    })
  }
  else{
    return;
    };
  });

  $("#checkbook").click(function () {
    const username = $("#username").val();
    const from = $("#ajaxfrom").val();
    const to = $("#ajaxto").val();
    const param = {
      from,
      to,
      username,
      _csrf: tokken,
    };

    $.ajax({
      url: "/hotel/manager/checkbookbyusername",
      method: "post",
      data: param,
      dataType: "json",
      statusCode: {
        200: function () {
          $("#bookckresult").val(1);
        },
        409: function () {
          $("#bookckresult").val(3);
        },
      },
    });
  });

  $("#managerbookbtn").click(function () {
    const days = sessionStorage.getItem("day");
    const result = $("#bookckresult").val();
    if (result == 0) {
      alert("예약확인을 해야 합니다.");
      return;
    }
    if (result == 3) {
      alert("이미 예약이 존재합니다");
      return;
    }
    if (result == 1) {
      const totalprice = $("#totalprice").val();
      const booker = $("#booker").val();
      const booktel = $("#booktel").val();
      const username = $("#username").val();
      const gradename = $("#gradename").val();
      const totalcnt = $("#totalcnt").val();
      const from = $("#ajaxfrom").val();
      const to = $("#ajaxto").val();
      const bfcheckbox = $("#bfcheckbox").is(":checked");
      const dicheckbox = $("#dicheckbox").is(":checked");
      const paycode = 1;
      const param = {
        username,
        from,
        to,
        gradename,
        totalcnt,
        booker,
        booktel,
        bfcheckbox,
        dicheckbox,
        paycode,
        _csrf: tokken,
      };
      if (booker == "" && booktel == "") {
        alert("예약자와 예약자 전화번호를 입력하세요");
      }

      $.ajax({
        url: "/hotel/manager/checkin",
        method: "post",
        dataType: "json",
        data: param,
      });
      window.close();
    }
  });
  $(document).on('click','#managerdinnerbook',function (){
    const booker = $('#booker').val();
    const from = $('#from').val();
    const totalcnt = $('#totalcnt').val()
    $.ajax({
      url:"/hotel/client/managerdinner",
      method:'post',
      data :{
        _csrf:tokken,
        from,
        booker,totalcnt
      },
      statusCode:{
        200:function (){
          alert('예약이 완료되었습니다')
          location.reload();
        },
        409:function (){
          alert('이미 예약이 존제합니다')
          location.reload();
        },
        400:function (){
          alert('사용자를 찾을수 없습니다')
          location.reload();
        }
      }
    })
  })

  $("#resradio").click(function () {
    $("#bookradio").prop("checked", false);
    const carea = $("#search");
    carea.empty();
    $("#managerroomlistdiv").empty();
    const html = ` <input type="text" id = "booker" placeholder="사용자" height="2px;">
              <input type="date" id = "from" placeholder="체크인날짜 입력" height="2px;">
              <input type="number" id = "totalcnt" min="1" value="1" max="4">
              <button class="btn btn-secondary" style="width: 70px;" id="managerdinnerbook">예약</button>`;
    carea.append(html);
  });
  $("#bookradio").click(function () {
    $("#resradio").prop("checked", false);
    const carea = $("#search");
    carea.empty();
    const html = ` 
              <input type="date" id = "from" placeholder="체크인날짜 입력" height="2px;">
                <input type="date" id = "to" placeholder="체크아웃날짜 입력" height="2px;">
              <button class="btn btn-secondary" style="width: 70px;" id="managerbook">예약</button>
              <span>숙박일 :</span>
              <span id = "daymanager"></span>
              `;
    carea.append(html);
  });
  $("#managerroomlistdiv").on("click", "#booklistbtn", function () {
    const gradeName = $(this).parent().prev().prev().prev().val();
    const gradeprice = $(this).parent().prev().children("#gradeprice").text();
    const from = $("#ajaxfrom").val();
    const to = $("#ajaxto").val();
    const day = parseInt($("#daymanager").text());
    const totalprice = gradeprice * day;
    window.open(
      `/hotel/client/roomdetail?grandname=${gradeName}&price=${totalprice}&from=${from}&to=${to}`,
      "managerbookdetail",
      "scrollbar=no, width=600, height=700, top=150, left=600"
    );
  });
  $(document).on("click", "#managerbook", async function () {
    const DAY_IN_MS = 1000 * 60 * 60 * 24; // 1 day in milliseconds
    const from = $("#from").val();
    const to = $("#to").val();
    const datefrom = new Date(from);
    const fromday =
      datefrom.getDate() < 10 ? "0" + datefrom.getDate() : datefrom.getDate(); // day가 한 자리 수인 경우에만 0을 붙임
    const frommonth =
      datefrom.getMonth() + 1 < 10
        ? "0" + (datefrom.getMonth() + 1)
        : datefrom.getMonth() + 1;
    const fromyer = datefrom.getFullYear();
    const lastfrom = frommonth + "/" + fromday + "/" + fromyer;
    const dateto = new Date(to);
    const today =
      dateto.getDate() < 10 ? "0" + dateto.getDate() : dateto.getDate(); // day가 한 자리 수인 경우에만 0을 붙임
    const tomenth =
      dateto.getMonth() + 1 < 10
        ? "0" + (dateto.getMonth() + 1)
        : dateto.getMonth() + 1;
    const toyer = dateto.getFullYear();
    const lastto = tomenth + "/" + today + "/" + toyer;
    const diffTime = Math.abs(dateto - datefrom);
    const diffDays = Math.ceil(diffTime / DAY_IN_MS); // 올림하여 일 수 계산
    // 총 일 수 출력
    const roomlist = await $.ajax({
      url: "/hotel/manager/checkroom",
      method: "post",
      dataType: "json",
      data: {
        from: lastfrom,
        to: lastto,
        _csrf: tokken,
      },
    });
    $("#ajaxfrom").val(lastfrom);
    $("#ajaxto").val(lastto);
    printManagerRoomList(roomlist);
    sessionStorage.setItem("days", diffDays);
    $("#daymanager").text(diffDays + "일");
  });

  $("#totalcnt").click(function () {
    const $totalcnt = $("#totalcnt").val();
    if ($totalcnt >= 4) {
      alert("최대 4명까지 가능합니다.");
    }
    if ($totalcnt <= 1) {
      alert("최소 인원은 1명입니다");
      return;
    }
  });
  $("#mypagepickdate").change(function () {});

  const $footer = $("#bookfooter");
  var clientKey = "test_ck_D5GePWvyJnrK0W0k6q8gLzN97Eoq";
  var tossPayments = TossPayments(clientKey);

  if (
    sessionStorage.getItem("from") != null &&
    sessionStorage.getItem("to") != null
  ) {
    const $from = sessionStorage.getItem("from");
    const $to = sessionStorage.getItem("to");
    const $day = sessionStorage.getItem("day");
    const param = {
      from: $from,
      to: $to,
      _csrf: tokken,
    };
    const $roomlist = await $.ajax({
      url: "/hotel/client/roombook",
      data: param,
      dataType: "json",
      method: "post",
    });
    $("#from").val($from);
    $("#to").val($to);
    $(".night").text($day);
    printRoomList($roomlist, $footer);
    sessionStorage.removeItem("from");
    sessionStorage.removeItem("to");
    sessionStorage.removeItem("day");
  }

  $("#paybookbtn").click(function () {
    const gradeName = $("#roomname").text();
    const $from = $("#from").val();
    const $to = $("#to").val();
    const $bfcheckbox = $("#bfcheckbox").val();
    const $booker = $("#booker").val();
    const $booktel = $("#booktel").val();
    const $totalcnt = $("#totalcnt").val();
    const $dicheckbox = $("#dicheckbox").val();
    if ($from == "" || $to == "") {
      alert("필수 입력사항을 확인하세요");
      return;
    } else {
      $("#paymentPopup").attr("style", "display:block;");
      $("#paymentPopup-box").attr("style", "display:block");
      $("html").attr("style", "overflow: hidden");
      $("#chargeInquiry").attr("style", "z-index:1; pointer-events: none;");
    }
    const param = {
      from: $from,
      to: $to,
      totalcnt: $totalcnt,
      gradename: gradeName,
      bfcheckbox: $bfcheckbox,
      dicheckbox: $dicheckbox,
      booker: $booker,
      booktel: $booktel,
      _csrf: tokken,
    };
    $.ajax({
      url: "/hotel/client/chekin",
      data: param,
      dataType: "json",
      method: "post",
    });
  });
  $("#choosePayment-box1").click(function () {
    const totalprice = $("#roominfo").text();
    const gradename = $("#roomname").text();
    parseInt(totalprice);
    const mylevel = $('#boookmylevel').text();
    kakaojs({ totalprice, gradename });
  });
  $("#choosePayment-box2").click(function () {
    const totalprice = $("#roominfo").text();
    const gradename = $("#roomname").text();
    parseInt(totalprice);
    let uuid = self.crypto.randomUUID();
    tossPayments.requestPayment("TOSSPAY", {
      amount: totalprice,
      orderId: uuid,
      orderName: gradename,
      successUrl: "http://reacthms.kro.kr/pay/toss_success",
      failUrl: "http://reacthms.kro.kr/hotel/main",
    });
  });

  $("#paymentPopup-btn").click(function () {
    $("#paymentPopup").attr("style", "display:none;");
    $("#paymentPopup-box").attr("style", "display:none");
    $("html").attr("style", "overflow:none");
    $("#chargeInquiry").attr("style", "pointer-events: none;");
  });

  $("#bookfooter").on("click", "#booklistbtn", function () {
    if ($("#booker").val() == "" || $("#booktel").val() == "") {
      alert("예약자와 연락처는 필수 입력 사항입니다");
    } else {
      const gradeName = $(this).parent().prev().prev().prev().val();
      const gradeprice = $(this).parent().prev().children("#gradeprice").text();

      printtotalprice(gradeName, gradeprice);
    }
  });

  $("#managerinfobox").click(async function () {
    if ($("#username").val() == "") {
      alert("사용자 이름을 입력하세요");
      location.reload();
    } else {
      const $myinfo = $("#managerinfobox").is(":checked");
      if ($myinfo == true) {
        const res = await $.ajax({
          url: "/hotel/client/myinfo",
          method: "post",
          data: {
            username: $("#username").val(),
            _csrf: tokken,
          },
          success: function (res) {
            $("#booker").val(res.name).attr("disabled", true);
            $("#booktel").val(res.tel).attr("disabled", true);
          },
        });
      }
      if ($myinfo == false) {
        $("#booker").val("").attr("disabled", false);
        $("#booktel").val("").attr("disabled", false);
      }
    }
  });



  $("#reservation-btn").click(async function () {
    const $foote2 = $("#bookfooter").html();
    if ($foote2.length == 0) {
      const $from = $("#from").val();
      const $to = $("#to").val();
      const param = {
        from: $from,
        to: $to,
        _csrf: tokken,
      };

      try {
        $roomlist = await $.ajax({
          url: "/hotel/client/roombook",
          data: param,
          dataType: "json",
          method: "post",
        });

        printRoomList($roomlist, $footer);
        $.ajax({
          url: "/hotel/client/myinfo",
          method: "post",
          data: {
            _csrf: tokken,
          },
          success: function (res) {
            $("#booker").val(res.name).attr("disabled", true);
            $("#booktel").val(res.tel).attr("disabled", true);
          },
        });



      } catch (err) {
        if (err.status == "409") {
          alert("해당 날짜에 이미 예약이 있습니다");
          location.reload();
        }
      }
    } else {
      alert("이미 선택 하셨습니다");
    }

  });

  $("#dinnerbook").click(async function () {
    const $bookdate = $("#from").val();
    const $booktel = $("#booktel").val();
    const $totalcnt = $("#totalcnt").val();
    const $booker = $("#booker").val();
    const param2 = {
      from: $bookdate,
      booktel: $booktel,
      totalcnt: $totalcnt,
      booker: $booker,
      _csrf: tokken,
    };
    if ($booker == "" && $booktel == "") {
      alert("필수 입력사항을 확인하세요");
      return;
    } else {
      try {
        await $.ajax({
          url: "/hotel/client/dinnerbook",
          data: param2,
          dataType: "json",
          method: "post",
          statusCode:{
            409:function (){
              alert("이미 예약이 존재합니다")
              location.reload();
            },
            200:function (){
              $("#paymentPopup").attr("style", "display:block;");
              $("#paymentPopup-box").attr("style", "display:block");
              $("html").attr("style", "overflow: hidden");
              $("#chargeInquiry").attr("style", "z-index:1; pointer-events: none;");
            }
          }
        });
      } catch (err) {
        console.log(err);
      }
    }

  });
});

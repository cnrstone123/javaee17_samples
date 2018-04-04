var worker = new Worker("worker.sql.js");
/**
//-- problem#1.in chrome-security,
    when worker cannot be loaded in chrome,
    cause chrome doesnot allowed to load the-local-system-file (dbfile)
    but firefox is ok.
//-- from: so.com
//-- solution#1 (current)...
    (in chrome, on startup) uses argument allowing local-access with "chrome.exe --allow-file-access-from-files"
//-- solution#2... make worker to be object like belows...
     new Worker(URL.createObjectURL(new Blob(["("+worker_function.toString()+")()"], {type: 'text/javascript'})));
*/
var app00 = angular.module("app00", []);
app00.run(function ($rootScope) {
	$rootScope.ggvar = "gloval variable.. in root-scope";
});
app00.controller("SelectCtrler", function ($scope, $http) {
	$scope.LstSGGALL = [[{ code: "*", ming: "乡级行政区 시/군/구" }],
	[{ code: "*", ming: "乡级行政区 시/군/구 (*)" }, { code: "110", ming: "종로구__110" }, { code: "140", ming: "중구__140" }, { code: "170", ming: "용산구__170" }, { code: "200", ming: "성동구__200" }, { code: "215", ming: "광진구__215" }, { code: "230", ming: "동대문구__230" }, { code: "260", ming: "중랑구__260" }, { code: "290", ming: "성북구__290" }, { code: "305", ming: "강북구__305" }, { code: "320", ming: "도봉구__320" }, { code: "350", ming: "노원구__350" }, { code: "380", ming: "은평구__380" }, { code: "410", ming: "서대문구__410" }, { code: "440", ming: "마포구__440" }, { code: "470", ming: "양천구__470" }, { code: "500", ming: "강서구__500" }, { code: "530", ming: "구로구__530" }, { code: "545", ming: "금천구__545" }, { code: "560", ming: "영등포구__560" }, { code: "590", ming: "동작구__590" }, { code: "620", ming: "관악구__620" }, { code: "650", ming: "서초구__650" }, { code: "680", ming: "강남구__680" }, { code: "710", ming: "송파구__710" }, { code: "740", ming: "강동구__740" }],
	[{ code: "*", ming: "乡级行政区 시/군/구 (*)" }, { code: "110", ming: "수원시__110" }, { code: "111", ming: "수원시 장안구__111" }, { code: "113", ming: "수원시 권선구__113" }, { code: "115", ming: "수원시 팔달구__115" }, { code: "117", ming: "수원시 영통구__117" }, { code: "130", ming: "성남시__130" }, { code: "131", ming: "성남시 수정구__131" }, { code: "133", ming: "성남시 중원구__133" }, { code: "135", ming: "성남시 분당구__135" }, { code: "150", ming: "의정부시__150" }, { code: "170", ming: "안양시__170" }, { code: "171", ming: "안양시 만안구__171" }, { code: "173", ming: "안양시 동안구__173" }, { code: "190", ming: "부천시__190" }, { code: "210", ming: "광명시__210" }, { code: "220", ming: "평택시__220" }, { code: "250", ming: "동두천시__250" }, { code: "270", ming: "안산시__270" }, { code: "271", ming: "안산시 상록구__271" }, { code: "273", ming: "안산시 단원구__273" }, { code: "280", ming: "고양시__280" }, { code: "281", ming: "고양시 덕양구__281" }, { code: "285", ming: "고양시 일산동구__285" }, { code: "287", ming: "고양시 일산서구__287" }, { code: "290", ming: "과천시__290" }, { code: "310", ming: "구리시__310" }, { code: "360", ming: "남양주시__360" }, { code: "370", ming: "오산시__370" }, { code: "390", ming: "시흥시__390" }, { code: "410", ming: "군포시__410" }, { code: "430", ming: "의왕시__430" }, { code: "450", ming: "하남시__450" }, { code: "460", ming: "용인시__460" }, { code: "461", ming: "용인시 처인구__461" }, { code: "463", ming: "용인시 기흥구__463" }, { code: "465", ming: "용인시 수지구__465" }, { code: "480", ming: "파주시__480" }, { code: "500", ming: "이천시__500" }, { code: "550", ming: "안성시__550" }, { code: "570", ming: "김포시__570" }, { code: "590", ming: "화성시__590" }, { code: "610", ming: "광주시__610" }, { code: "630", ming: "양주시__630" }, { code: "650", ming: "포천시__650" }, { code: "670", ming: "여주시__670" }, { code: "800", ming: "연천군__800" }, { code: "820", ming: "가평군__820" }, { code: "830", ming: "양평군__830" }],
	[{ code: "*", ming: "乡级行政区 시/군/구 (*)" }, { code: "110", ming: "춘천시__110" }, { code: "130", ming: "원주시__130" }, { code: "150", ming: "강릉시__150" }, { code: "170", ming: "동해시__170" }, { code: "190", ming: "태백시__190" }, { code: "210", ming: "속초시__210" }, { code: "230", ming: "삼척시__230" }, { code: "720", ming: "홍천군__720" }, { code: "730", ming: "횡성군__730" }, { code: "750", ming: "영월군__750" }, { code: "760", ming: "평창군__760" }, { code: "770", ming: "정선군__770" }, { code: "780", ming: "철원군__780" }, { code: "790", ming: "화천군__790" }, { code: "800", ming: "양구군__800" }, { code: "810", ming: "인제군__810" }, { code: "820", ming: "고성군__820" }, { code: "830", ming: "양양군__830" }],
	[{ code: "*", ming: "乡级行政区 시/군/구 (*)" }, { code: "110", ming: "청주시__110" }, { code: "111", ming: "청주시 상당구__111" }, { code: "112", ming: "청주시 서원구__112" }, { code: "113", ming: "청주시 흥덕구__113" }, { code: "114", ming: "청주시 청원구__114" }, { code: "130", ming: "충주시__130" }, { code: "150", ming: "제천시__150" }, { code: "720", ming: "보은군__720" }, { code: "730", ming: "옥천군__730" }, { code: "740", ming: "영동군__740" }, { code: "745", ming: "증평군__745" }, { code: "750", ming: "진천군__750" }, { code: "760", ming: "괴산군__760" }, { code: "770", ming: "음성군__770" }, { code: "800", ming: "단양군__800" }],
	[{ code: "*", ming: "乡级行政区 시/군/구 (*)" }, { code: "130", ming: "천안시__130" }, { code: "131", ming: "천안시 동남구__131" }, { code: "133", ming: "천안시 서북구__133" }, { code: "150", ming: "공주시__150" }, { code: "180", ming: "보령시__180" }, { code: "200", ming: "아산시__200" }, { code: "210", ming: "서산시__210" }, { code: "230", ming: "논산시__230" }, { code: "250", ming: "계룡시__250" }, { code: "270", ming: "당진시__270" }, { code: "710", ming: "금산군__710" }, { code: "760", ming: "부여군__760" }, { code: "770", ming: "서천군__770" }, { code: "790", ming: "청양군__790" }, { code: "800", ming: "홍성군__800" }, { code: "810", ming: "예산군__810" }, { code: "825", ming: "태안군__825" }],
	[{ code: "*", ming: "乡级行政区 시/군/구 (*)" }, { code: "110", ming: "동구__110" }, { code: "140", ming: "중구__140" }, { code: "170", ming: "서구__170" }, { code: "200", ming: "유성구__200" }, { code: "230", ming: "대덕구__230" }],
	[{ code: "*", ming: "乡级行政区 시/군/구 (*)" }, { code: "110", ming: "중구__110" }, { code: "140", ming: "동구__140" }, { code: "170", ming: "남구__170" }, { code: "185", ming: "연수구__185" }, { code: "200", ming: "남동구__200" }, { code: "237", ming: "부평구__237" }, { code: "245", ming: "계양구__245" }, { code: "260", ming: "서구__260" }, { code: "710", ming: "강화군__710" }, { code: "720", ming: "옹진군__720" }],
	[{ code: "*", ming: "乡级行政区 시/군/구 (*)" }, { code: "110", ming: "세종특별자치시__110" }],
	[{ code: "*", ming: "乡级行政区 시/군/구 (*)" }, { code: "110", ming: "중구__110" }, { code: "140", ming: "동구__140" }, { code: "170", ming: "서구__170" }, { code: "200", ming: "남구__200" }, { code: "230", ming: "북구__230" }, { code: "260", ming: "수성구__260" }, { code: "290", ming: "달서구__290" }, { code: "710", ming: "달성군__710" }],
	[{ code: "*", ming: "乡级行政区 시/군/구 (*)" }, { code: "110", ming: "포항시__110" }, { code: "111", ming: "포항시 남구__111" }, { code: "113", ming: "포항시 북구__113" }, { code: "130", ming: "경주시__130" }, { code: "150", ming: "김천시__150" }, { code: "170", ming: "안동시__170" }, { code: "190", ming: "구미시__190" }, { code: "210", ming: "영주시__210" }, { code: "230", ming: "영천시__230" }, { code: "250", ming: "상주시__250" }, { code: "280", ming: "문경시__280" }, { code: "290", ming: "경산시__290" }, { code: "720", ming: "군위군__720" }, { code: "730", ming: "의성군__730" }, { code: "750", ming: "청송군__750" }, { code: "760", ming: "영양군__760" }, { code: "770", ming: "영덕군__770" }, { code: "820", ming: "청도군__820" }, { code: "830", ming: "고령군__830" }, { code: "840", ming: "성주군__840" }, { code: "850", ming: "칠곡군__850" }, { code: "900", ming: "예천군__900" }, { code: "920", ming: "봉화군__920" }, { code: "930", ming: "울진군__930" }, { code: "940", ming: "울릉군__940" }],
	[{ code: "*", ming: "乡级行政区 시/군/구 (*)" }, { code: "110", ming: "중구__110" }, { code: "140", ming: "서구__140" }, { code: "170", ming: "동구__170" }, { code: "200", ming: "영도구__200" }, { code: "230", ming: "부산진구__230" }, { code: "260", ming: "동래구__260" }, { code: "290", ming: "남구__290" }, { code: "320", ming: "북구__320" }, { code: "350", ming: "해운대구__350" }, { code: "380", ming: "사하구__380" }, { code: "410", ming: "금정구__410" }, { code: "440", ming: "강서구__440" }, { code: "470", ming: "연제구__470" }, { code: "500", ming: "수영구__500" }, { code: "530", ming: "사상구__530" }, { code: "710", ming: "기장군__710" }],
	[{ code: "*", ming: "乡级行政区 시/군/구 (*)" }, { code: "110", ming: "중구__110" }, { code: "140", ming: "남구__140" }, { code: "170", ming: "동구__170" }, { code: "200", ming: "북구__200" }, { code: "710", ming: "울주군__710" }, { code: "110", ming: "undefined__110" }],
	[{ code: "*", ming: "乡级行政区 시/군/구 (*)" }, { code: "120", ming: "창원시__120" }, { code: "121", ming: "창원시 의창구__121" }, { code: "123", ming: "창원시 성산구__123" }, { code: "125", ming: "창원시 마산합포구__125" }, { code: "127", ming: "창원시 마산회원구__127" }, { code: "129", ming: "창원시 진해구__129" }, { code: "170", ming: "진주시__170" }, { code: "220", ming: "통영시__220" }, { code: "240", ming: "사천시__240" }, { code: "250", ming: "김해시__250" }, { code: "270", ming: "밀양시__270" }, { code: "310", ming: "거제시__310" }, { code: "330", ming: "양산시__330" }, { code: "720", ming: "의령군__720" }, { code: "730", ming: "함안군__730" }, { code: "740", ming: "창녕군__740" }, { code: "820", ming: "고성군__820" }, { code: "840", ming: "남해군__840" }, { code: "850", ming: "하동군__850" }, { code: "860", ming: "산청군__860" }, { code: "870", ming: "함양군__870" }, { code: "880", ming: "거창군__880" }, { code: "890", ming: "합천군__890" }],
	[{ code: "*", ming: "乡级行政区 시/군/구 (*)" }, { code: "110", ming: "전주시__110" }, { code: "111", ming: "전주시 완산구__111" }, { code: "113", ming: "전주시 덕진구__113" }, { code: "130", ming: "군산시__130" }, { code: "140", ming: "익산시__140" }, { code: "180", ming: "정읍시__180" }, { code: "190", ming: "남원시__190" }, { code: "210", ming: "김제시__210" }, { code: "710", ming: "완주군__710" }, { code: "720", ming: "진안군__720" }, { code: "730", ming: "무주군__730" }, { code: "740", ming: "장수군__740" }, { code: "750", ming: "임실군__750" }, { code: "770", ming: "순창군__770" }, { code: "790", ming: "고창군__790" }, { code: "800", ming: "부안군__800" }],
	[{ code: "*", ming: "乡级行政区 시/군/구 (*)" }, { code: "110", ming: "목포시__110" }, { code: "130", ming: "여수시__130" }, { code: "150", ming: "순천시__150" }, { code: "170", ming: "나주시__170" }, { code: "230", ming: "광양시__230" }, { code: "710", ming: "담양군__710" }, { code: "720", ming: "곡성군__720" }, { code: "730", ming: "구례군__730" }, { code: "770", ming: "고흥군__770" }, { code: "780", ming: "보성군__780" }, { code: "790", ming: "화순군__790" }, { code: "800", ming: "장흥군__800" }, { code: "810", ming: "강진군__810" }, { code: "820", ming: "해남군__820" }, { code: "830", ming: "영암군__830" }, { code: "840", ming: "무안군__840" }, { code: "860", ming: "함평군__860" }, { code: "870", ming: "영광군__870" }, { code: "880", ming: "장성군__880" }, { code: "890", ming: "완도군__890" }, { code: "900", ming: "진도군__900" }, { code: "910", ming: "신안군__910" }],
	[{ code: "*", ming: "乡级行政区 시/군/구 (*)" }, { code: "110", ming: "동구__110" }, { code: "140", ming: "서구__140" }, { code: "155", ming: "남구__155" }, { code: "170", ming: "북구__170" }, { code: "200", ming: "광산구__200" }],
	[{ code: "*", ming: "乡级行政区 시/군/구 (*)" }, { code: "110", ming: "제주시__110" }, { code: "130", ming: "서귀포시__130" }]
	];
	$scope.LstLawUMDALL = [[{ code: "*", ming: "镇：法定洞 읍/면/동" }]];
	$scope.LstGovUMDALL = [[{ code: "*", ming: "镇：行政洞 읍/면/동" }]];
	$scope.LstSIDO = [{ code: "*", ming: "县级行政区 시/도 (*)" }, { code: "11", ming: "서울특별시__11" }, { code: "41", ming: "경기도__41" }, { code: "42", ming: "강원도__42" }, { code: "43", ming: "충청북도__43" }, { code: "44", ming: "충청남도__44" }, { code: "30", ming: "대전광역시__30" }, { code: "28", ming: "인천광역시__28" }, { code: "36", ming: "세종특별자치시__36" }, { code: "27", ming: "대구광역시__27" }, { code: "47", ming: "경상북도__47" }, { code: "26", ming: "부산광역시__26" }, { code: "31", ming: "울산광역시__31" }, { code: "48", ming: "경상남도__48" }, { code: "45", ming: "전라북도__45" }, { code: "46", ming: "전라남도__46" }, { code: "29", ming: "광주광역시__29" }, { code: "50", ming: "제주특별자치도__50" }];
	$scope.LstSGG = $scope.LstSGGALL[0]; $scope.cdSIDO = $scope.LstSIDO[0]; $scope.cdSGG = $scope.LstSGG[0];
	$scope.LstLawUMD = $scope.LstLawUMDALL[0]; $scope.cdLawUMD = $scope.LstLawUMD[0];
	$scope.LstGovUMD = $scope.LstGovUMDALL[0]; $scope.cdGovUMD = $scope.LstGovUMD[0];

	$scope.changedSIDO = function () { // event-handler of cdSGG-select-click
		$scope.LstSGG = $scope.LstSGGALL[document.getElementById('cdSIDO').selectedIndex];
		$scope.cdSGG = $scope.LstSGG[0];
		$scope.LstLawUMD = $scope.LstLawUMDALL[0]; $scope.cdLawUMD = $scope.LstLawUMD[0];
		$scope.LstGovUMD = $scope.LstGovUMDALL[0]; $scope.cdGovUMD = $scope.LstGovUMD[0];
	};
	$scope.changedSGG = function () {  // event-handler of cdLawUMD-select-click
		if ($scope.cdSIDO.code === '*' || $scope.cdSGG.code === '*') return;
		var cdkey = $scope.cdSIDO.code + '' + $scope.cdSGG.code,
			orgnm = $scope.cdSIDO.ming.split('__')[0] + ' ';
		$scope.execQry(orgnm, "SELECT org_cd||','||org_nm FROM tb_organ WHERE use_yn='존재' AND org_cd LIKE '" + cdkey + "%00';");
	};
	$scope.makeGovUMD = function (evt) {  // event-handler of cdGovUMD-select-click
		// http GET from 정보통신부-API
	};
	$scope.execQry = function (orgnm, strqry) {
		tic();
		worker.onmessage = function (event) {
			var results = event.data.results, rows = [], vals = [], parsedObj;
			toc("Executing SQL");
			tic();
			rows.push('{"code":"*", "ming":"镇：法定洞 읍/면/동 (*)"}');
			for (var row = 0; row < results.length; row++) {
				results[row].values.map(function (item) {
					vals = item[0].split(',');
					rows.push('{"code":"' + vals[0].substr(5, 3) + '", "ming":"' + vals[1].replace(orgnm, '') + '__' + vals[0].substr(5, 3) + '"}');
				});
			}
			toc("Displaying =" + $scope.cdSGG.ming + ", rows count=" + rows.length);
			parsedObj = JSON.parse('{"options":[' + rows.join(',') + ']}');
			if(parsedObj) {
				$scope.$apply(function(){
					// console.log('//-- options len='+parsedObj.options.length);
					$scope.LstLawUMD = parsedObj.options;
					$scope.cdLawUMD = $scope.LstLawUMD[0];
					$scope.cdGovUMD = $scope.LstGovUMD[0];
				});
			}
			toc("ok.");
			outputElm.textContent = "Fetch ok(" + $scope.cdSGG.ming + "). rows count=" + rows.length;
		}
		worker.postMessage({ action: 'exec', sql: strqry });
		outputElm.textContent = "Fetching results...";
	};
	/**
	 * Failed to load http://apis.data.go.kr/B552015/NpsBplcInfoInqireService/getBassInfoSearch:
	 *
	 * Response to preflight request doesn't pass access control check:
	 * No 'Access-Control-Allow-Origin' header is present on the requested resource.
	 * Origin 'file://' is therefore not allowed access.
	 * The response had HTTP status code 500.
	 *
	 * maybe..
	 * because chrome-startup-command is ???... chrome.exe --allow-file-access-from-files
	 */
	$scope.search = function(){
		var argName  = $scope.compName,
			argRegNo = $scope.compRegNo;
		if(!argName && !argRegNo){
			alert('company-name or company-registration-no, one of two-fields is required.');
			return;
		} else {
			argRegNo = (argRegNo ? argRegNo.replace(/-/g,'') : '');
			if(!!argRegNo && argRegNo.length > 0 && argRegNo.length < 6){
				alert('company-registration-no must have to 6-digit at least.');
				return;
			}
		}

		console.log('//-- argName ='+argName+', argRegNo ='+argRegNo);
		var opers   = ['/getBassInfoSearch','/getDetailInfoSearch','/getPdAccToSttuInfoSearch'],
			url     = 'http://apis.data.go.kr/B552015/NpsBplcInfoInqireService'+opers[0],
			reqConf = {
				method:"POST",
				responseType: "json",
				url:url,
				headers:{
					"crossOrigin":true,
					"accept":"application/json"
				},
				data:{
					"ldong_addr_mgpl_dg_cd": $scope.cdSIDO.code||'',
					"ldong_addr_mgpl_sggu_cd": $scope.cdSGG.code||'',
					"ldong_addr_mgpl_sggu_emd_cd": $scope.cdLawUMD.code||'',
					"wkpl_nm": argName||'',
					"bzowr_rgst_no": argRegNo||'',
					"pageNo": 10,
					"numOfRows": 1
				}
			};
	/*
		// try #1
		$http(reqConf).then(function(response){
			// success
			outputElm.textContent = 'result is...'+response.data;
		}, function(herror){
			// err-handling
			outputElm.textContent = 'error is...'+herror.xhrStatus; // statusText;
		});
	*/
	/*
		//-- try #2
		var req_conf = {
			url: url,
			crossOrigin: true,
			dataType: 'jsonp', // if not specified, complains these-messages...
								// Cross-Origin Request Blocked:
								// The Same Origin Policy disallows reading the remote resource at
								// http://apis.data.go.kr/B552015/NpsBplcInfoInqireService/getBassInfoSearch.
								// (Reason: CORS header ‘Access-Control-Allow-Origin’ missing).
			type: 'POST',
			contentType: 'application/json; charset=utf-8',
			data:{
				"ldong_addr_mgpl_dg_cd": $scope.cdSIDO.code||'',
				"ldong_addr_mgpl_sggu_cd": $scope.cdSGG.code||'',
				"ldong_addr_mgpl_sggu_emd_cd": $scope.cdLawUMD.code||'',
				"wkpl_nm": argName||'',
				"bzowr_rgst_no": argRegNo||'',
				"pageNo": 10,
				"numOfRows": 1,
				"serviceKey": "bRoF%2F2z3SYjfACQIccyBBQSGBKoE39%2FoXFZ3fQVYyn8Tc5nC0O9COTbwHWXPy7%2FogNbwHzIIO8RVhZ1d9u7GgQ%3D%3D"
			},
			accept: 'application/json; charset=utf-8'
		};
		if(req_conf.data["ldong_addr_mgpl_dg_cd"].length !== 2 || req_conf.data["ldong_addr_mgpl_sggu_cd"].length !== 3 || req_conf.data["ldong_addr_mgpl_sggu_emd_cd"].length !== 3){
			alert("公司住所 is not correct.!!");
			return;
		}
		$.ajax(req_conf).done(function (data) {
			console.log('//-- '+data);
		}).fail(function (xhr, textStatus, error) {
			var title, message;
			switch (xhr.status) {
			case 403:
				title = xhr.responseJSON.errorSummary;
				message = 'Please login to your server before running the test.';
				break;
			default:
				title = 'Invalid URL or Cross-Origin Request Blocked';
				message = 'You must explictly add this site (' + window.location.origin + ') to the list of allowed websites in your server.';
				break;
			}
			console.log('textStatus='+textStatus+', error='+JSON.stringify(error)+', errObj='+error);
			console.log('req_conf='+JSON.stringify(req_conf));
			console.log('status='+xhr.status+', '+message);
		});
	*/
		// try #3
		var dataIn = {
			"ldong_addr_mgpl_dg_cd": $scope.cdSIDO.code||'',
			"ldong_addr_mgpl_sggu_cd": $scope.cdSGG.code||'',
			"ldong_addr_mgpl_sggu_emd_cd": $scope.cdLawUMD.code||'',
			"wkpl_nm": argName||'',
			"bzowr_rgst_no": argRegNo||'',
			"pageNo": 10,
			"numOfRows": 1,
			"serviceKey": "bRoF%2F2z3SYjfACQIccyBBQSGBKoE39%2FoXFZ3fQVYyn8Tc5nC0O9COTbwHWXPy7%2FogNbwHzIIO8RVhZ1d9u7GgQ%3D%3D"
		};
		console.log('//-- '+JSON.stringify(dataIn));
		$.get(url, JSON.stringify(dataIn), function(data){
				console.log('//-- '+'aaa');
			}, 'jsonp');

			// .done(function (data) {
			// 	console.log('//-- '+data);
			// }).fail(function (xhr, textStatus, error) {
			// 	var title, message;
			// 	switch (xhr.status) {
			// 	case 403:
			// 		title = xhr.responseJSON.errorSummary;
			// 		message = 'Please login to your server before running the test.';
			// 		break;
			// 	default:
			// 		title = 'Invalid URL or Cross-Origin Request Blocked';
			// 		message = 'You must explictly add this site (' + window.location.origin + ') to the list of allowed websites in your server.';
			// 		console.log('textStatus='+textStatus+', error='+JSON.stringify(error)+', errObj='+error);
			// 		console.log('dataIn='+JSON.stringify(dataIn));
			// 		console.log('status='+xhr.status+', '+message);
			// 		break;
			// 	}
			//}),

	}; // end of searchBtn
});

/**
 * from: https://www.data.go.kr/information/qna/new.do
 * (insert button: "저장")
 * cause by not supporting IE9이상, Chrome, Firefox
 */
function btn_insert_click(){
		if(!document.frm.qestnSj.value){
			alert("제목을 입력해 주세요.");
			setFocus('qestnSj');
			return;
		}
		else if(!document.frm.qestnCn.value){
			alert("내용을 입력해 주세요.");
			setFocus('qestnCn');
			return;
		}else{
			if(confirm("등록하시겠습니까?")){
				if(agent != "mac") {
					if(fileUploadAt != "Y"){
						File.Upload("file");
					}else{
						webfilter(fileInfo);
					}
				}else{
/* 						commonAPI.uploadAllFile(
							// 파일전송 완료
							function(data){
								commonAPI.setLoadingBar("file", false); 	// 로딩바 삭제

								var saveFileList = {};

								for(var i = 1; i < 6; i++){
									if(data["fileName"+i]){
										saveFileList["fileId"+i] = data["fileName"+i].name;
										saveFileList["fileName"+i] = data["fileName"+i].callName;
									}
								}

								commonAPI.submitData(
									{ DS_LIST :  [$scope.rowData] , DS_FILE_LIST : [ saveFileList ] }
									, "/pubr/pot/qna/irosQnaManage/insertQna.do"
									, function(dataList){	 // 성공 콜백
										commonAPI.messageBox({
											msg : commonAPI.message("CMIF0007"),
											close : function(result){
															if(result){
																commonAPI.openPage("pubr/pot/qna/irosQnaManage/openIrosSelectQnaMngListPage.do");
															}
														}
										});
									}
								);
							},
							// 에러 발생 처리
							function (errMsg){
								commonAPI.messageBox("저장 과정 중에서 문제가 발생했습니다.<br>" + errMsg);
							}
						); */
				}
			}
		}
	}

// var execBtn = document.getElementById("execute");
var cdLawUMDElm = document.getElementById('cdLawUMD');
var outputElm = document.getElementById('output');
var errorElm = document.getElementById('error');
var commandsElm = document.getElementById('commands'); // textarea-sql
var dbFileElm = document.getElementById('dbfile');
//var savedbElm = document.getElementById('savedb');

// Start the worker in which sql.js will run
//var worker = new Worker("worker.sql.js");
worker.onerror = error;

// Open a database
worker.postMessage({action:'open'});

// Connect to the HTML element we 'print' to
function print(text) {
    outputElm.innerHTML = text.replace(/\n/g, '<br>');
}
function error(e) {
  console.log(e);
	errorElm.style.height = '2em';
	errorElm.textContent = e.message;
}

function noerror() {
		errorElm.style.height = '0';
}

/**
 * func: optionCreate()
 * added by leeys, at 2018.03.23.FRI.PM06
 */
var optionCreate = function(){
    function makeTag(rowline){
        var vals   = rowline.split(',');
        var tagStr = '<option value="'+vals[0]+'">'+vals[1]+'__'+vals[0]+'</option>';
        return tagStr;
    }
    return function(dataRows){
        var rows = dataRows.map(function(v){ return makeTag(v); });
        var html = '<option value="*">镇：法定洞 읍/면/동 (*)</option>'+rows.join('');
        return html;
    };
}();

// Run a command in the database
function execute(commands) {
	tic();
	worker.onmessage = function(event) {
		var results = event.data.results;
		toc("Executing SQL");

		tic();
		outputElm.innerHTML = "";
		for (var i=0; i<results.length; i++) {
			outputElm.appendChild(tableCreate(results[i].columns, results[i].values));
		}
		toc("Displaying results");
	}
	worker.postMessage({action:'exec', sql:commands});
	outputElm.textContent = "Fetching results...";
}

// Create an HTML table
var tableCreate = function () {
  function valconcat(vals, tagName) {
    if (vals.length === 0) return '';
    var open = '<'+tagName+'>', close='</'+tagName+'>';
    return open + vals.join(close + open) + close;
  }
  return function (columns, values){
    var tbl  = document.createElement('table');
        // tbl.className = 'col-sm-offset-4 col-sm-4';
    var html = '<thead>' + valconcat(columns, 'th') + '</thead>';
    var rows = values.map(function(v){ return valconcat(v, 'td'); });
    html += '<tbody>' + valconcat(rows, 'tr') + '</tbody>';
	  tbl.innerHTML = html;
    return tbl;
  }
}();

// Execute the commands when the button is clicked
function execEditorContents (qry) {
	noerror()
	//execute (editor.getValue() + ';');
  execute(qry);
}
function execSubCodeQry(orgcd){
  noerror();
  toc("retreiving from organ.db");
  // Show the schema of the loaded database
  //editor.setValue("SELECT `name`, `sql`\n  FROM `sqlite_master`\n  WHERE type='table';");
//var qry = "SELECT name, sql FROM sqlite_master WHERE type='index';"
  // var qry = "SELECT tb_organ.org_cd||','||tb_organ.org_nm FROM organ.tb_organ WHERE tb_organ.use_yn='존재' and tb_organ.org_cd LIKE '"+orgcd+"%';";
  var qry = "SELECT * FROM tb_organ WHERE use_yn='존재' AND org_cd LIKE '"+orgcd+"%';";
  execute(qry);
}
//execBtn.addEventListener("click", execEditorContents, true);

// Performance measurement functions
var tictime;
if (!window.performance || !performance.now) {window.performance = {now:Date.now}}
function tic () {tictime = performance.now()}
function toc(msg) {
	var dt = performance.now()-tictime;
	console.log('\t\t'+(msg||'toc') + ": " + dt + "ms");
}

// Add syntax highlihjting to the textarea
/*
var editor = CodeMirror.fromTextArea(commandsElm, {
    mode: 'text/x-mysql',
    viewportMargin: Infinity,
    indentWithTabs: true,
    smartIndent: true,
    lineNumbers: true,
    matchBrackets : true,
    autofocus: true,
		extraKeys: {
			"Ctrl-Enter": execEditorContents,
			"Ctrl-S": savedb,
		}
});
*/

// Load a db from a file
dbFileElm.onchange = function() {
	var f = dbFileElm.files[0];
	var r = new FileReader();
	console.log('//-- f is '+typeof f+', r is '+ typeof r);
	console.log('//-- f is ='+dbFileElm.files[0].name + ', size =' + dbFileElm.files[0].size);
	r.onload = function() {
		worker.onmessage = function () {
			toc("Loading database from file");
			// Show the schema of the loaded database
			//editor.setValue("SELECT `name`, `sql`\n  FROM `sqlite_master`\n  WHERE type='table';");
			var qry = "SELECT name, sql FROM sqlite_master WHERE type='table';"
              + "SELECT name, sql FROM sqlite_master WHERE type='index';"
			execEditorContents(qry);
		};
		tic();
		try {
			worker.postMessage({action:'open',buffer:r.result}, [r.result]);
		}
		catch(exception) {
			worker.postMessage({action:'open',buffer:r.result});
		}
	}
	r.readAsArrayBuffer(f);
}

// Save the db to a file
/*
function savedb () {
	worker.onmessage = function(event) {
		toc("Exporting the database");
		var arraybuff = event.data.buffer;
		var blob = new Blob([arraybuff]);
		var a = document.createElement("a");
		a.href = window.URL.createObjectURL(blob);
		a.download = "sql.db";
		a.onclick = function() {
			setTimeout(function() {
				window.URL.revokeObjectURL(a.href);
			}, 1500);
		};
		a.click();
	};
	tic();
	worker.postMessage({action:'export'});
}
savedbElm.addEventListener("click", savedb, true);
*/
/**
	var db = new SQL.Database();
	// if (!db.preparse){
	//   console.log('//-- db.preparse ='+db.preparse);
	//   return;
	// }
	var selected = evt.target.value,
		stmt = db.preparse("SELECT org_cd||','||org_nm FROM tb_organ WHERE tb_organ.useyn='존재' and tb_organ.org_cd like $fore||'%'"),
		targetEL = document.getElementById('cdLawUMD'),
		innerTagStr = '<option value="*">镇：法定洞 읍/면/동 (*)</option>';
	stmt.getAsObject({$fore:selected});
	stmt.bind({$fore:selected});
	while(stmt.step()){
		var datarow = stmt.getAsObject().split(',');
		innerTagStr += '<option value="'+datarow[0]+'">'+datarow[1]+' ('+datarow[0]+')</option>';
	}
	targetEL.innerHTML = innerTagStr;
 */
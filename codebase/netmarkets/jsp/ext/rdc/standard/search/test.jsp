<%@ include file="/netmarkets/jsp/util/begin.jspf"%>
<script type="text/javascript">
var XMLHttpReq;
if(window.XMLHttpRequest){
    XMLHttpReq = new XMLHttpRequest();
 }else if(window.ActiveXObject){
  try{
    XMLHttpReq = new ActiveXObject("MSXML2.XMLHTTP");
		}catch(e){
			try{
			 XMLHttpReq = new ActiveXObject("Mircsoft.XMLHTTP");
			}catch(e1){}
		}
	}
if(XMLHttpReq){
	XMLHttpReq.onreadystatechange = function(){
		alert('readyState='+XMLHttpReq.readyState);
		alert('status='+XMLHttpReq.status);
		if(XMLHttpReq.readyState == 4){
			
			if(XMLHttpReq.status == 200){
				document.write(XMLHttpReq.responseText);
				var mainform = document.getElementById('mainform');
				if(mainform){
					var p=document.createElement('div');
					p.id='test';
					p.innerHtml = '123';
					mainform.parentNode.appendChild(p);
					
					//mainform.parentNode.replaceChild(p,mainform);
					//alert(mainform.parentNode.removeChild(mainform));
				}
			}
		}
	};
	XMLHttpReq.open("GET",'netmarkets/jsp/ext/rdc/standard/search/123.html',true);
	XMLHttpReq.send(null);
}
	
</script>

<%@ include file="/netmarkets/jsp/util/end.jspf"%>
function writeCookie(name,value,day)
{
expire="";
expire=new Date();
expire.setTime(expire.getTime()+day*24*3600*1000);
expire=expire.toGMTString();
document.cookie=name+"="+escape(value)+";expires="+expire;
}
function readCookie(name)
{
cookieValue="";
search1=name+"=";
if(document.cookie.length>0)
{
offset=document.cookie.indexOf(search1) ;
if(offset!=-1)
{
       offset+=search1.length;
end=document.cookie.indexOf(";",offset);
if(end==-1)
    end= document.cookie.length;
cookieValue=unescape(document.cookie.substring(offset,end)); 
}
}
return cookieValue;
}
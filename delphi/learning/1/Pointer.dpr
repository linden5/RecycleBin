program Pointer;

{$APPTYPE CONSOLE}

uses
  SysUtils;
  
var
  p:^Integer;
  n:Integer;
  
begin
  p:= @n;// p points to n
  n := 98;
  writeln(pinteger(p)^);// 类型转化读取p
  pinteger(p)^ := 78; // 设置n的值
  writeln(pinteger(p)^);
  readln;
end.

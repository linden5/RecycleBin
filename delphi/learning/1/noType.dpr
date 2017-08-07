program noType;

{$APPTYPE CONSOLE}

uses
  SysUtils;

function Equal(var source, dest; size : integer) : boolean;
type
  TBytes = array[0..MaxInt - 1] of Byte;
var
  N : Integer;
begin
  N := 0;
  while(N < size) and (TBytes(Dest)[N] = TBytes(source)[N]) do
    Inc(N);
    Equal := N = size;
end;

var
  s : string;
  source, dest : string;
begin
  source := 'afadf';
  dest := ';lakdf;k';
  writeln(Equal(source, dest, 7));
  readln(s);
  { TODO -oUser -cConsole Main : Insert code here }
end.

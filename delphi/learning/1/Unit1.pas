unit Unit1;

interface

uses
  Windows, Messages, SysUtils, Variants, Classes, Graphics, Controls, Forms,
  Dialogs, StdCtrls;

type
  TForm1 = class(TForm)
    Button1: TButton;
    Button2: TButton;
    procedure Button1Click(Sender: TObject);
    procedure Button2Click(Sender: TObject);
  private
    { Private declarations }
  public
    { Public declarations }
  end;

  T1 = class
    procedure M1; virtual; abstract;
  end;
  T2 = class(T1)
    procedure M1; override;
  end;

var
  Form1: TForm1;

implementation

{$R *.dfm}
procedure T2.M1;
begin
  showMessage('T2.M1');
end;

procedure TForm1.Button1Click(Sender: TObject);
var
  obj : T1;
begin
  obj := T1.Create;
  obj.M1;
  FreeAndNil(obj);
end;

procedure TForm1.Button2Click(Sender: TObject);
var
  obj : T2;
begin
  obj := T2.Create;
  obj.M1;
  FreeAndNil(obj);
end;

end.
 
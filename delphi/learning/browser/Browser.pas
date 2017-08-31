unit Browser;

interface

uses
  Windows, Messages, SysUtils, Variants, Classes, Graphics, Controls, Forms,
  Dialogs, ShellApi, Registry, StdCtrls;

type
  TForm1 = class(TForm)
    LauchBrowserButton: TButton;
    URLEdit: TEdit;
    GetDefaultBrowserButton: TButton;
    IEVersionButton: TButton;
    procedure LauchBrowserButtonClick(Sender: TObject);
    procedure GetDefaultBrowserButtonClick(Sender: TObject);
    procedure IEVersionButtonClick(Sender: TObject);
  private
    { Private declarations }
  public
    { Public declarations }
  end;

var
  Form1: TForm1;

implementation

{$R *.dfm}

Const
  KEY_WOW64_32KEY = $0200;
  KEY_WOW64_64KEY = $0100;

function GetDefaultBrowser:string;
var
  reg: TRegistry;
begin
  reg := TRegistry.Create(KEY_READ OR KEY_WOW64_32KEY OR KEY_WOW64_64KEY);
  try
    reg.RootKey := HKEY_CLASSES_ROOT;
    reg.OpenKey('\http\shell\open\command',false);
    result:=reg.ReadString('');
    reg.CloseKey;
  finally
    reg.Free;
  end;
end;

function GetIEVersion:string;
var
  reg: TRegistry;
begin
  reg := TRegistry.Create(KEY_READ OR KEY_WOW64_32KEY OR KEY_WOW64_64KEY);
  try
    reg.RootKey := HKEY_LOCAL_MACHINE;
    reg.OpenKey('Software\Microsoft\Internet Explorer',false);
    result:=reg.ReadString('Version');
    reg.CloseKey;
  finally
    reg.Free;
  end;
end;

procedure TForm1.LauchBrowserButtonClick(Sender: TObject);
var
  URLToOpen: PAnsiChar;
begin
  URLToOpen := PAnsiChar(AnsiString('http://' + URLEdit.Text));
  ShowMessage(URLToOpen);
  // The following commented code works, leave it here undeleted as a referrence
  // ShellExecute(Application.Handle, nil, 'http://cn.bing.com', nil, nil, SW_SHOWNORMAL);
  ShellExecute(Application.Handle, nil, URLToOpen, nil, nil, SW_SHOWNORMAL);
end;

procedure TForm1.GetDefaultBrowserButtonClick(Sender: TObject);
var
  DefaultBrowser: string;
begin
  DefaultBrowser := GetDefaultBrowser;
  ShowMessage(DefaultBrowser);
end;

procedure TForm1.IEVersionButtonClick(Sender: TObject);
var
  IEVersion: string;
begin
  IEVersion := GetIEVersion;
  ShowMessage(IEVersion);
end;

end.

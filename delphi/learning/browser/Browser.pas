unit Browser;

interface

uses
  Windows, Messages, SysUtils, Variants, Classes, Graphics, Controls, Forms,
  Dialogs, StdCtrls;

type
  TForm1 = class(TForm)
    LauchBrowserButton: TButton;
    URLEdit: TEdit;
    GetDefaultBrowserButton: TButton;
    IEVersionButton: TButton;
    SetKey: TEdit;
    SetValue: TEdit;
    SetToRedisButton: TButton;
    GetFromRedisButton: TButton;
    GetKey: TEdit;
    Label1: TLabel;
    Label2: TLabel;
    Label3: TLabel;
    Label4: TLabel;
    GetValue: TEdit;
    RandomStrButton: TButton;
    RedisDelButton: TButton;
    procedure LauchBrowserButtonClick(Sender: TObject);
    procedure GetDefaultBrowserButtonClick(Sender: TObject);
    procedure IEVersionButtonClick(Sender: TObject);
    procedure SetToRedisButtonClick(Sender: TObject);
    procedure GetFromRedisButtonClick(Sender: TObject);
    procedure RandomStrButtonClick(Sender: TObject);
    procedure RedisDelButtonClick(Sender: TObject);
  private
    { Private declarations }
  public
    { Public declarations }
  end;

var
  Form1: TForm1;

implementation

uses BrowserUtils, RedisUtils, RandomSequence;

{$R *.dfm}

{浏览器打开新页面}
procedure TForm1.LauchBrowserButtonClick(Sender: TObject);
var
  URLToOpen: string;
begin
  URLToOpen := 'http://' + URLEdit.Text + '?usercode=' + SetKey.Text;
  // The following commented code works, leave it here undeleted as a referrence
  //ShellExecute(Application.Handle, nil, 'http://cn.bing.com', nil, nil, SW_SHOWNORMAL);
  TBrowserUtils.OpenInDefaultNotIE(URLToOpen);
end;

{取得默认浏览器}
procedure TForm1.GetDefaultBrowserButtonClick(Sender: TObject);
begin
  ShowMessage(TBrowserUtils.GetDefaultBrowser);
end;

{取得IE版本}
procedure TForm1.IEVersionButtonClick(Sender: TObject);
begin
  ShowMessage(TBrowserUtils.GetIEVersion);
end;

procedure TForm1.SetToRedisButtonClick(Sender: TObject);
var
  Key: string;
  Value: string;
  Redis: TRedisUtils;
  RedisResult: string;
begin
  Key := SetKey.Text;
  Value := SetValue.Text;

  Redis := TRedisUtils.Create();
  RedisResult := Redis.SetToRedis(Key, Value);
  ShowMessage(RedisResult);
end;

procedure TForm1.GetFromRedisButtonClick(Sender: TObject);
var
  Key: string;
  Value: string;
  Redis: TRedisUtils;
begin
  Key := SetKey.Text;

  Redis := TRedisUtils.Create();
  Value := Redis.GetFromRedis(Key);
  GetValue.Text := Value;

end;

procedure TForm1.RandomStrButtonClick(Sender: TObject);
var
  str : string;
begin
  str := TRandomSequence.Generate;
  ShowMessage(str);
  SetKey.Text := str;
end;

procedure TForm1.RedisDelButtonClick(Sender: TObject);
var
  Key: string;
  RedisResult: string;
  Redis: TRedisUtils;
begin
  Key := SetKey.Text;

  Redis := TRedisUtils.Create();
  RedisResult := Redis.DelFromRedis(Key);
  ShowMessage(RedisResult);
end;

end.

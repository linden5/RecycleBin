unit BrowserUtils;

interface

type
  TBrowserUtils = class
  public
    class procedure OpenInDefaultBrowser(URL: string);
    class procedure OpenInDefaultNotIE(URL: string);
    class function GetDefaultBrowser:string;
    class function GetIEVersion:string;
  end;

implementation

uses Windows, ShellApi, Registry, Forms, SysUtils, StrUtils, Classes, Dialogs;

Const
  KEY_WOW64_32KEY = DWORD($0200);
  KEY_WOW64_64KEY = DWORD($0100);

{ 在默认浏览器中打开网址  }
class procedure TBrowserUtils.OpenInDefaultBrowser(URL: string);
var
  URLToOpen: PAnsiChar;
begin
  URLToOpen := PAnsiChar(AnsiString(URL));
  // The following commented code works, leave it here undeleted as a referrence
  // ShellExecute(Application.Handle, nil, 'http://cn.bing.com', nil, nil, SW_SHOWNORMAL);
  ShellExecute(Application.Handle, nil, URLToOpen, nil, nil, SW_SHOWNORMAL);
end;

{ 判断是否为IE, 建议其安装火狐，之后在默认浏览器中打开网址  }
class procedure TBrowserUtils.OpenInDefaultNotIE(URL: string);
var
  BrowserName: string;
  BrowserVersion: string;
  IsIE: Boolean;
  VersionToken: TStrings;
begin
  BrowserName := TBrowserUtils.GetDefaultBrowser;
  IsIE := AnsiContainsStr(BrowserName, 'iexplore');
  if IsIE then
  begin
    BrowserVersion := TBrowserUtils.GetIEVersion;
    VersionToken := TStringList.Create;
    VersionToken.Delimiter := '.';
    VersionToken.DelimitedText := BrowserVersion;
    if StrToInt(VersionToken[0]) < 9 then
    begin
      ShowMessage('您的IE浏览器版本过低，建议安装最新版火狐浏览器，然后设置为默认浏览器，之后再使用该功能');
      TBrowserUtils.OpenInDefaultBrowser('http://www.firefox.com.cn/');
    end
  end
  else
    TBrowserUtils.OpenInDefaultBrowser(URL);
end;

{ 取得默认浏览器 }
class function TBrowserUtils.GetDefaultBrowser:string;
var
  reg: TRegistry;
begin
  reg := TRegistry.Create(KEY_ALL_ACCESS); // win7 64位用 KEY_ALL_ACCESS就可以读出来了 ，用其它的都不好使
  try
    reg.RootKey := HKEY_CLASSES_ROOT;
    reg.OpenKey('\http\shell\open\command',false);
    result:=reg.ReadString('');
    reg.CloseKey;
  finally
    reg.Free;
  end;
end;

{ 取得IE版本 }
class function TBrowserUtils.GetIEVersion:string;
var
  reg: TRegistry;
begin
  reg := TRegistry.Create(KEY_ALL_ACCESS);
  try
    reg.RootKey := HKEY_LOCAL_MACHINE;
    reg.OpenKey('Software\Microsoft\Internet Explorer',false);
    
    result:=reg.ReadString('Version');
    reg.CloseKey;
  finally
    reg.Free;
  end;
end;

end.

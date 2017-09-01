unit RedisTCPClient;

interface

uses
  Redis;

function getTCPClient : ITCPClient;

implementation

uses
  IdTCPClient, IdGlobal;

type
  TRedisTCPClient = class (TInterfacedObject, ITCPClient) // Adapter [ITCPClient <-> TIdTCPClient]
  private
    fClient : TIdTCPClient;
  public
    constructor Create;
    destructor Destroy; override;
    procedure Connect(aHost : string; aPort : integer);
    procedure Disconnect;
    procedure Write(aText : AnsiString);
    procedure WriteLn(aText : AnsiString);
    function ReadLn : AnsiString;
    function IsConnected : boolean;
    function ReadBytes(count : integer) : string;
  end;

function getTCPClient : ITCPClient;
begin
  result := TRedisTCPClient.Create;
end;

{ TRedisTCPClient }

procedure TRedisTCPClient.Connect(aHost: string; aPort: integer);
begin
  fClient.Host := aHost;
  fClient.Port := aPort;
  fClient.Connect(30000);
end;

constructor TRedisTCPClient.Create;
begin
  fClient := TIdTCPClient.Create(nil);
end;

destructor TRedisTCPClient.Destroy;
begin
  fClient.Free;
  inherited;
end;

procedure TRedisTCPClient.Disconnect;
begin
  fClient.Disconnect;
end;

function TRedisTCPClient.IsConnected: boolean;
begin
  result := fClient.Connected;
end;

function TRedisTCPClient.ReadBytes(count: integer): string;
begin
  if count > -1 then
    Result := fClient.ReadLn
  else
    Result := '';
end;

function TRedisTCPClient.ReadLn: AnsiString;
begin
  Result := fClient.ReadLn;
end;

procedure TRedisTCPClient.Write(aText: AnsiString);
begin
  fClient.Write(aText);
end;

procedure TRedisTCPClient.WriteLn(aText: AnsiString);
begin
  fClient.WriteLn(aText);
end;

end.
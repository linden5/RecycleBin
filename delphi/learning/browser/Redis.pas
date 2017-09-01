unit Redis;

interface

uses
  SysUtils;

type
  ITCPClient = interface
    ['{7D6EA759-4B3A-4791-A5AE-D6D4E79E06AB}']
    procedure Connect(aHost: string; aPort: integer);
    procedure Disconnect;
    procedure Write(aText: AnsiString);
    procedure WriteLn(aText: AnsiString);
    function ReadLn: AnsiString;
    function ReadBytes(count : integer) : string;
    function IsConnected : boolean;
  end;

  IRedisConnection = interface
    ['{C85CE959-409F-4D82-A594-0558BA01D55F}']
    function getHost: string;
    procedure setHost(const Value: string);
    property Host: string read getHost write setHost;

    function getPort: integer;
    procedure setPort(const Value: integer);
    property Port: integer read getPort write setPort;

    function getClient: ITCPClient;
    procedure setClient(const Value: ITCPClient);
    property TCPClient: ITCPClient read getClient write setClient;

    function Auth(aPassword: string): string;
    function ExecuteQuery(aQuery: string): string;
  end;

  TRedis = class
  public
    class function getConnection(aHost: string = 'localhost';
                                 aPort: integer = 6379;
                                 aTCPClient: ITCPClient = nil) : IRedisConnection;
  end;

  RedisException = class(Exception);

implementation

uses
  RedisConnection;

{ TRedis }

class function TRedis.getConnection(aHost: string; aPort: integer; aTCPClient: ITCPClient)
  : IRedisConnection;
begin
  result := getRedisConnection(aTCPClient);
  result.Host := aHost;
  result.Port := aPort;
end;

end.

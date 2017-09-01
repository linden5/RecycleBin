unit RedisUtils;

interface

uses Redis;

type
  TRedisUtils = class
  private
    fConnection: IRedisConnection;
  public
    constructor Create; overload;
    constructor Create(Host: string; Port: Integer); overload;
    destructor Destroy;
    function SetToRedis(Key: string; Value: string): string;
    function GetFromRedis(Key: string): string; 
  end;

implementation

constructor TRedisUtils.Create;
begin
  fConnection := TRedis.getConnection('localhost', 6379);
end;

constructor TRedisUtils.Create(Host: string; Port: Integer);
begin
  fConnection := TRedis.getConnection(Host, Port)
end;

destructor TRedisUtils.Destroy;
begin
  fConnection._Release;
end;

function TRedisUtils.SetToRedis(Key: string; Value: string): string;
var
  Query : string;
begin
  Query := 'set ' + Key + ' ' + Value;
  Result := fConnection.ExecuteQuery(Query);
end;

function TRedisUtils.GetFromRedis(Key: string): string;
var
  Query : string;
begin
  Query := 'get ' + Key;
  Result := fConnection.ExecuteQuery(Query);
end;

end.

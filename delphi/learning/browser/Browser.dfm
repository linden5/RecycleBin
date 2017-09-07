object Form1: TForm1
  Left = 227
  Top = 135
  Width = 928
  Height = 480
  Caption = 'Form1'
  Color = clBtnFace
  Font.Charset = DEFAULT_CHARSET
  Font.Color = clWindowText
  Font.Height = -11
  Font.Name = 'MS Sans Serif'
  Font.Style = []
  OldCreateOrder = False
  PixelsPerInch = 96
  TextHeight = 13
  object GroupBox1: TGroupBox
    Left = 32
    Top = 16
    Width = 385
    Height = 385
    Caption = #22522#26412#21151#33021#27979#35797
    TabOrder = 0
    object Label1: TLabel
      Left = 32
      Top = 224
      Width = 27
      Height = 13
      Caption = 'Value'
    end
    object Label2: TLabel
      Left = 32
      Top = 200
      Width = 18
      Height = 13
      Caption = 'Key'
    end
    object Label3: TLabel
      Left = 32
      Top = 272
      Width = 18
      Height = 13
      Caption = 'Key'
    end
    object Label4: TLabel
      Left = 32
      Top = 296
      Width = 27
      Height = 13
      Caption = 'Value'
    end
    object Label5: TLabel
      Left = 40
      Top = 40
      Width = 22
      Height = 13
      Caption = 'URL'
    end
    object GetDefaultBrowserButton: TButton
      Left = 200
      Top = 72
      Width = 169
      Height = 25
      Caption = #33719#21462#40664#35748#27983#35272#22120
      TabOrder = 0
      OnClick = GetDefaultBrowserButtonClick
    end
    object GetFromRedisButton: TButton
      Left = 200
      Top = 272
      Width = 121
      Height = 25
      Caption = #20174'redis'#33719#21462
      TabOrder = 1
      OnClick = GetFromRedisButtonClick
    end
    object GetKey: TEdit
      Left = 64
      Top = 272
      Width = 121
      Height = 21
      TabOrder = 2
      Text = 'GetKey'
    end
    object GetValue: TEdit
      Left = 64
      Top = 296
      Width = 121
      Height = 21
      TabOrder = 3
      Text = 'GetValue'
    end
    object IEVersionButton: TButton
      Left = 200
      Top = 104
      Width = 169
      Height = 25
      Caption = #33719#21462'IE'#29256#26412
      TabOrder = 4
      OnClick = IEVersionButtonClick
    end
    object LauchBrowserButton: TButton
      Left = 200
      Top = 40
      Width = 169
      Height = 25
      Caption = #22312#40664#35748#27983#35272#22120#20013#25171#24320
      TabOrder = 5
      OnClick = LauchBrowserButtonClick
    end
    object RandomStrButton: TButton
      Left = 200
      Top = 168
      Width = 169
      Height = 25
      Caption = #20135#29983#38543#26426#23383#31526#20018
      TabOrder = 6
      OnClick = RandomStrButtonClick
    end
    object RedisDelButton: TButton
      Left = 200
      Top = 224
      Width = 121
      Height = 25
      Caption = #20174'redis'#21024#38500
      TabOrder = 7
      OnClick = RedisDelButtonClick
    end
    object SetKey: TEdit
      Left = 64
      Top = 200
      Width = 121
      Height = 21
      TabOrder = 8
      Text = 'SetKey'
    end
    object SetToRedisButton: TButton
      Left = 200
      Top = 200
      Width = 121
      Height = 25
      Caption = #23384#21040'redis'
      TabOrder = 9
      OnClick = SetToRedisButtonClick
    end
    object SetValue: TEdit
      Left = 64
      Top = 224
      Width = 121
      Height = 21
      TabOrder = 10
      Text = 'SetValue'
    end
    object URLEdit: TEdit
      Left = 64
      Top = 40
      Width = 121
      Height = 21
      TabOrder = 11
      Text = 'localhost:8080/btejump'
    end
  end
  object GroupBox2: TGroupBox
    Left = 512
    Top = 24
    Width = 257
    Height = 369
    Caption = #27169#25311#36339#36716
    TabOrder = 1
    object LoginButton: TButton
      Left = 64
      Top = 48
      Width = 129
      Height = 25
      Caption = #30331#24405#27169#25311
      TabOrder = 0
      OnClick = LoginButtonClick
    end
    object LogoutButton: TButton
      Left = 64
      Top = 128
      Width = 129
      Height = 25
      Caption = #36864#20986#27169#25311
      TabOrder = 1
      OnClick = LogoutButtonClick
    end
    object AddressButton: TButton
      Left = 64
      Top = 88
      Width = 129
      Height = 25
      Caption = #36339#36716#21040#22320#22336#24211
      TabOrder = 2
      OnClick = AddressButtonClick
    end
  end
end

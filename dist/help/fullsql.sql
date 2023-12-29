create table duthi_daihoc
go 
use duthi_daihoc;
CREATE TABLE KhoiThi (
  khoithi NVARCHAR(10) PRIMARY KEY NOT NULL,
  MonThi1 NVARCHAR(10),
  MonThi2 NVARCHAR(10),
  MonThi3 NVARCHAR(10)
);
CREATE TABLE ThiSinh (
  SoBaoDanh INT PRIMARY KEY NOT NULL,
  HoTen NVARCHAR(255) NOT NULL,
  DiaChi NVARCHAR(255) NOT NULL,
  MucUuTien NVARCHAR(255) NOT NULL,
  KhoiThi NVARCHAR(10),
  FOREIGN KEY (KhoiThi) REFERENCES KhoiThi(khoithi)
);
INSERT INTO KhoiThi (khoiThi, MonThi1, MonThi2, MonThi3) 
VALUES 
(N'A', N'Toán', N'Lý', N'Hóa'),
(N'B', N'Văn', N'Sử', N'Địa'),
(N'C', N'Tiếng Anh', N'Sinh học', N'GDCD');
INSERT INTO ThiSinh (SoBaoDanh, HoTen, DiaChi, MucUuTien, KhoiThi) 
VALUES 
(1, N'Nguyễn Văn A', N'Hà Nội', N'Ưu tiên 1', 'A'),
(2, N'Trần Thị B', N'Hồ Chí Minh', N'Ưu tiên 2', 'B'),
(3, N'Phạm Thị C', N'Đà Nẵng', N'Ưu tiên 1', 'C'),
(4, N'Hoàng Văn D', N'Hải Phòng', N'Không ưu tiên', 'A'),
(5, N'Nguyễn Thị E', N'Cần Thơ', N'Ưu tiên 2', 'B');

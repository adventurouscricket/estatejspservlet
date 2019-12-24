<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Mr Henry</title>
</head>
<body>
	<div class="main-content">
		<div class="main-content-inner">
			<div class="breadcrumbs ace-save-state" id="breadcrumbs">
				<ul class="breadcrumb">
					<li>
						<i class="ace-icon fa fa-home home-icon"></i>
						<a href="#">Trang chủ</a>
					</li>
					<li class="active">Thêm sản phẩm</li>
				</ul>
			</div>
			<div class="page-content">
				<div class="row">
					<div class="col-xs-12">
						<div class="form-group paddingButtom">
							<label class="col-sm-2">Tên sản phẩm</label>
							<div class="col-sm-10"><input type="text" class="form-control" name="name" value="${model.name}"/></div>
						</div>
						<div class="form-group paddingButtom">
							<label class="col-sm-2">Quận</label>
							<div class="col-sm-10">
								<select class="form-control" id="">
									<option>Hello</option>
									<option>Hello</option>
									<option>Hello</option>
								</select>
							</div>
						</div>
						<div class="form-group paddingButtom">
							<label class="col-sm-2">Phường</label>
							<div class="col-sm-10"><input type="text" class="form-control" name="ward" value="${model.ward}"/></div>
						</div>
						<div class="form-group paddingButtom">
							<label class="col-sm-2">Đường</label>
							<div class="col-sm-10"><input type="text" class="form-control" name="street" value="${model.street}"/></div>
						</div>
						<div class="form-group paddingButtom">
							<label class="col-sm-2">Kết cấu</label>
							<div class="col-sm-10"><input type="text" class="form-control" name="structure" value="${model.structure}"/></div>
						</div>
						<div class="form-group paddingButtom">
							<label class="col-sm-2">Số tầng hầm</label>
							<div class="col-sm-10"><input type="text" class="form-control" name="numberOfBasement" value="${model.numberOfBasement}"/></div>
						</div>
						<div class="form-group paddingButtom">
							<label class="col-sm-2">Diện tích sàn</label>
							<div class="col-sm-10"><input type="text" class="form-control" name="buildingArea" value="${model.buildingArea}"/></div>
						</div>
						<div class="form-group paddingButtom">
							<label class="col-sm-2">Hướng</label>
							<div class="col-sm-10"><input type="text" class="form-control" name="direction" value="${model.direction}"/></div>
						</div>
						<div class="form-group paddingButtom">
							<label class="col-sm-2">Hạng</label>
							<div class="col-sm-10"><input type="text" class="form-control" name="level" value="${model.level}"/></div>
						</div>
						<div class="form-group paddingButtom">
							<label class="col-sm-2">Diện tích thuê</label>
							<div class="col-sm-10"><input type="text" class="form-control" name="areaRent" value="${model.areaRent}"/></div>
						</div>
						<div class="form-group paddingButtom">
							<label class="col-sm-2">Mô tả</label>
							<div class="col-sm-10"><input type="text" class="form-control"/></div>
						</div>
						<div class="form-group paddingButtom">
							<label class="col-sm-2">Giá thuê</label>
							<div class="col-sm-10"><input type="text" class="form-control" name="costRent" value="${model.costRent}"/></div>
						</div>
						<div class="form-group paddingButtom">
							<label class="col-sm-2">Mô tả giá</label>
							<div class="col-sm-10"><input type="text" class="form-control" name="costDescription" value="${model.costDescription}"/></div>
						</div>
						<div class="form-group paddingButtom">
							<label class="col-sm-2">Phí dịch vụ</label>
							<div class="col-sm-10"><input type="text" class="form-control" name="serviceCost" value="${model.serviceCost}"/></div>
						</div>
						<div class="form-group paddingButtom">
							<label class="col-sm-2">Phí ô tô</label>
							<div class="col-sm-10"><input type="text" class="form-control" name="carCost" value="${model.carCost}"/></div>
						</div>
						<div class="form-group paddingButtom">
							<label class="col-sm-2">Phí mô tô</label>
							<div class="col-sm-10"><input type="text" class="form-control" name="motorBikeCost" value="${model.motorBikeCost}"/></div>
						</div>
						<div class="form-group paddingButtom">
							<label class="col-sm-2">Phí ngoài giờ</label>
							<div class="col-sm-10"><input type="text" class="form-control" name="overtimeCost" value="${model.overtimeCost}"/></div>
						</div>
						<div class="form-group paddingButtom">
							<label class="col-sm-2">Tiền điện</label>
							<div class="col-sm-10"><input type="text" class="form-control" name="electricityCost" value="${model.electricityCost}"/></div>
						</div>
						<div class="form-group paddingButtom">
							<label class="col-sm-2">Đặt cọc</label>
							<div class="col-sm-10"><input type="text" class="form-control" name="deposit" value="${model.deposit}"/></div>
						</div>
						<div class="form-group paddingButtom">
							<label class="col-sm-2">Thanh toán</label>
							<div class="col-sm-10"><input type="text" class="form-control" name="payment" value="${model.payment}"/></div>
						</div>
						<div class="form-group paddingButtom">
							<label class="col-sm-2">Thời hạn thuê</label>
							<div class="col-sm-10"><input type="text" class="form-control" name="timeRent" value="${model.timeRent}"/></div>
						</div>
						<div class="form-group paddingButtom">
							<label class="col-sm-2">Thời gian trang trí</label>
							<div class="col-sm-10"><input type="text" class="form-control" name="timeDecorator" value="${model.timeDecorator}"/></div>
						</div>
						<div class="form-group paddingButtom">
							<label class="col-sm-2">Tên quản lý</label>
							<div class="col-sm-10"><input type="text" class="form-control" name="managerName" value="${model.managerName}"/></div>
						</div>
						<div class="form-group paddingButtom">
							<label class="col-sm-2">Số ĐT</label>
							<div class="col-sm-10"><input type="text" class="form-control" name="managerPhone" value="${model.managerPhone}"/></div>
						</div>
						<div class="form-group paddingButtom">
							<label class="col-sm-2">Loại tòa nhà</label>
							<div class="fg-line col-sm-10">
								<label class="checkbox-inline"><input type="checkbox" name="buildingTypes" value="TANG_TRET"/>Tầng trệt</label>
								<label class="checkbox-inline"><input type="checkbox" name="buildingTypes" value="NGUYEN_CAN"/>Nguyên căn</label>
								<label class="checkbox-inline"><input type="checkbox" name="buildingTypes" value="NOI_THAT"/>Nội thất</label>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
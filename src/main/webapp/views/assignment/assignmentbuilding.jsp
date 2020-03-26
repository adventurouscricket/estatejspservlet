<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp"%>

<c:url var="assignmentURL" value="/admin-asgnbuilding?action=list&page=1&maxPageItem=5" />
<c:url var="assignmentAPI" value="/api-admin-assignmentbuilding" />
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
					<li class="active">Quản lý nhà</li>
				</ul>
			</div>
			<div class="page-content">
				<div class="row">
					<div class="col-xs-12">
						<form action="${assignmentURL }" method="get" id="formSubmit">
							<table class="table table-bordered">
								<thead>
									<tr>
										<th>Tên nhân viên</th>
										<th><input type="checkbox" value="" id="checkAll" /></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="item" items="${models }">
										<tr>
											<td>${item.fullName}</td>
											<td><input type="checkbox" id="checkbox${item.id }"
												value="${item.staffId }" ${item.isChecked ? 'checked' : '' } />
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<input type="hidden" name="action" value="list" />
						
							<input type="hidden" name="buildingId" value="${model.buildingId }" id="buildingId"/>
							
							<input type="hidden" value="${model.page }" id="page" name="page" />
							<input type="hidden" value="${model.maxPageItem }" id="maxPageItem" name="maxPageItem" />
							<input type="hidden" value="" id="sortName" name="sortName" />
							<input type="hidden" value="" id="sortBy" name="sortBy" />
						</form>
						<div class="row text-center btn-addsp">
							<a href="javascript:window.open('','_self').close();">
								<button class="btn btn-success" id="btnclose">Close</button>
							</a>
							<button class="btn btn-success" id="btnSave">Save change</button>
						</div>
						<div class="container">
								<ul class="pagination" id="pagination"></ul>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	
		var totalPages = ${model.totalPage};
		var currentPage = ${model.page};
		var visiblePages = ${model.maxPageItem};
		
		var limit = 5;
		$(function () {
			window.pagObj = $('#pagination').twbsPagination({
				totalPages: totalPages,
				visiblePages: 5,
				startPage: currentPage,
				onPageClick: function (event, page) {
					if (currentPage != page) {
						$('#maxPageItem').val(limit);
						$('#page').val(page);
						//$('#sortName').val('name');
						//$('#sortBy').val('ASC');
						//$('#type').val('list');
						$('#formSubmit').submit();
					}
				}
			});
		});
		
		$('#btnSave').click(function () {
			var data = {};
			var dataArr = $('tbody input[type=checkbox]:checked').map(function(){
				return $(this).val();
			}).get();
			
			var formData = $("#formSubmit").serializeArray();
			$.each(formData, function(index, v){
				data[""+v.name+""] = v.value;
			});
			
			data['staffIds'] = dataArr;
			addOrUpdateAssignmentBuilding(data)
		});
		
		function addOrUpdateAssignmentBuilding(data) {
			$.ajax({
				url:'${assignmentAPI }',
				data: JSON.stringify(data),
				type: 'PUT',
				contentType: 'application/json',
				dataType: 'json',
				success: function(data){
					window.location.href="${assignmentURL}&buildingId="+data.buildingId+"&message=insert_success";
				},
				error: function() {
					window.location.href="${buildingURL}&buildingId="+data.buildingId+"&message=error_system";
				}
			});
		}
	</script>
</body>
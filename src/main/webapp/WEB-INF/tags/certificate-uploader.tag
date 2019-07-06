<%@ tag language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>

<div id="certificateUploader" class="modal fade" role="dialog">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Upload certificate file</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-xs-12 form-group">
						<input id="certificateFile" name="certificateFile" type="file" multiple placeholder="Chose certificate image" />
					</div>
					<div class="col-xs-12 form-group">
						<input id="certificateName" name="certificateName" class="form-control" type="text" placeholder="Certificate name" required="required" />
					</div>
					<div class="col-xs-12 text-center">
						<a href="javascript:resume.certificates.add();" class="btn btn-primary">Add certificate</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
var resume = {
	
	initCertificateViewer : function() {
		$('a.certificate-link').click(function(e){
			e.preventDefault();
			var title = $(this).attr('data-title');
			$('#certificateViewer .modal-title').html(title);
			$('#certificateViewer .modal-body img').attr('src', $(this).attr('data-url'));
			$('#certificateViewer').modal({
				show : true
			});
		});
	},
	
	createDatePicker : function() {
		/* http://bootstrap-datepicker.readthedocs.org/en/latest/options.html */
		$('.datepicker').datepicker({
			autoclose : true,
			clearBtn : true
		});
	},
	
	createPhotoUploader : function() {
		//https://github.com/kartik-v/bootstrap-fileinput
		$('#profilePhoto').fileinput({
			uploadAsync: false,
			showUpload: false,
			allowedFileExtensions : ['jpg', 'png'],
			maxFileCount: 1
		});
		$('#profilePhoto').on('fileclear', function() {
			$('#currentPhoto').css('display', 'block');
		});
		$('#profilePhoto').on('fileloaded', function() {
			$('#currentPhoto').css('display', 'none');
		});
	},
	
	createCertificateUploader : function(csrfToken) {
		//https://github.com/kartik-v/bootstrap-fileinput
		$('#certificateFile').fileinput({
			uploadUrl: ctx + '/profile/edit/certificates/upload?_csrf=' + csrfToken,
			allowedFileExtensions : ['jpg', 'png'],
			maxFileCount : 1,
			showPreview : false
		});
		$('#certificateFile').on('fileuploaded', function(event, data, previewId, index) {
			var response = data.response;
			if (response.name != null) {
				if ($('#certificateName').val().trim() == '') {
					$('#certificateName').val(response.name);
				}
			}
			$('#certificateUploader').attr('data-small-url', response.smallUrl);
			$('#certificateUploader').attr('data-large-url', response.largeUrl);
		});
		$('#certificateFile').on('fileuploaderror', function(event, data, msg) {
			resume.showErrorDialog(messages.errorUploadCertificate);
		});
	},
	
	showErrorDialog : function(message) {
		alert(message);
	},
	
	moreProfiles : function(searchQuery) {
		var page = parseInt($('#profileContainer').attr('data-profile-number')) + 1;
		var total = parseInt($('#profileContainer').attr('data-profile-total'));
		if (page >= total) {
			$('#loadMoreIndicator').remove();
			$('#loadMoreContainer').remove();
			return;
		}
		var pathname = location.pathname;
		//var queryString = location.search.substring(1);
		
		var url = ctx + pathname +  '/fragment/more?page=' + page;
		if (searchQuery != undefined && searchQuery.trim() != '') {
			url += '&query=' + searchQuery;
		}
		
		$('#loadMoreContainer').css('display', 'none');
		$('#loadMoreIndicator').css('display', 'block');
		
		$.ajax({
			url : url,
			success : function(data) {
				$('#loadMoreIndicator').css('display', 'none');
				$('#profileContainer').append(data);
				$('#profileContainer').attr('data-profile-number', page);
				if (page >= total-1) {
					$('#loadMoreIndicator').remove();
					$('#loadMoreContainer').remove();
				} else {
					$('#loadMoreContainer').css('display', 'block');
				}
			},
			error : function(data) {
				$('#loadMoreIndicator').css('display', 'none');
				resume.alert('Error! Try again later...');
			}
		});
	},
	
	ui : {
		// http://handlebarsjs.com
		template : null,
		
		getTemplate : function() {
			if (resume.ui.template == null) {
				var source = $('#ui-block-template').html();
				resume.ui.template = Handlebars.compile(source);
			}
			return resume.ui.template;
		},
		
		addBlock : function() {
			var template = resume.ui.getTemplate();
			var container = $('#ui-block-container');
			var blockIndex = container.find('.ui-item').length;
			var context = {
					blockIndex : blockIndex
			};
			container.append(template(context));
			
			container.find('input.level-slider').slider();
		},
		
		updateSelect : function(thisObj) {
			if (thisObj.val() == '') {
				var selectedIdRef = thisObj.attr('data-ref-selected');
				$('#'+selectedIdRef).val('');
			}
		}
	},
	
	certificates : {
		showUploadDialog : function() {
			$('#certificateUploader').modal({
				show : true
			});
		},
		
		add : function() {
			var certificateName = $('#certificateName').val();
			//https://www.tjvantoll.com/2012/08/05/html5-form-validation-showing-all-error-messages/
			if (certificateName.trim() == '') {
				alert('certificate name is absent');
				return;
			}
			var template = resume.ui.getTemplate();
			var container = $('#ui-block-container');
			var blockIndex = container.find('.ui-item').length;
			var context = {
					blockIndex : blockIndex,
					name : $('#certificateName').val(),
					smallUrl : $('#certificateUploader').attr('data-small-url'),
					largeUrl : $('#certificateUploader').attr('data-large-url'),
			};
			container.append(template(context));
			$('#certificateUploader').modal('hide');
			$('#certificateName').val('');
			$('#certificateFile').fileinput('clear');
			
			resume.initCertificateViewer();
		}
	},
	
	hobbies : {
		errorTimeout : null,
		
		save : function() {
			var hobbies = '';
			var selectedHobbyButtons = $('.hobby-btn.active');
			var maxHobbies = parseInt($('#ui-block-container').attr('data-max-hobbies'));
			if (selectedHobbyButtons.length > maxHobbies) {
				var closeFunction = function() {
					$('#errorAlert').fadeOut('slow');
					if (resume.hobbies.errorTimeout != null) {
						clearTimeout(resume.hobbies.errorTimeout);
						resume.hobbies.errorTimeout = null;
					}
				};
				$('#errorAlert button').unbind('click');
				$('#errorAlert button').click(closeFunction);
				$('#errorAlert').fadeIn('slow');
				resume.hobbies.errorTimeout = setTimeout(closeFunction, 5000);
				return;
			}
			for (var i = 0; i < selectedHobbyButtons.length; i++) {
				hobbies += $(selectedHobbyButtons[i]).attr('data-hobby');
				if (i != selectedHobbyButtons.length - 1) {
					hobbies += ',';
				}
			}
			var _csrfToken = $('#hobbyContainer').attr('data-csrf-value');
			resume.post(ctx + '/profile/edit/hobbies', {
				hobbies : hobbies,
				_csrf : _csrfToken
			});
		}
	},
	
	post : function(path, params) {
		var form = document.createElement("form");
		form.setAttribute("method", 'post');
		form.setAttribute("action", path);
		for ( var key in params) {
			if (params.hasOwnProperty(key)) {
				var value = params[key];
				if (value != undefined) {
					var hiddenField = document.createElement("input");
					hiddenField.setAttribute("type", 'hidden');
					hiddenField.setAttribute("name", key);
					hiddenField.setAttribute("value", value);
					form.appendChild(hiddenField);
				}
			}
		}
		document.body.appendChild(form);
		form.submit();
	},
	
	logout : function() {
		var csrfParameter = $("meta[name='_csrf_parameter']").attr("content");
		var csrfToken = $("meta[name='_csrf']").attr("content");
		var url = ctx + '/sign-out';
		
		resume.post(url, {
			_csrf : csrfToken
		});
		
//		var form = $('<form>', {
//			action: url,
//			method: 'POST',
//		}).append($('<input>', {
//			name : csrfParameter,
//			value : csrfToken,
//			type : 'hidden'
//		}));
//		$('body').append(form);
//		form.submit();
	}
};
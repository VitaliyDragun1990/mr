var resume = {
	
	alert : function(message) {
		alert(message);
	},
	
	moreProfiles : function() {
		var page = parseInt($('#profileContainer').attr('data-profile-number')) + 1;
		var total = parseInt($('#profileContainer').attr('data-profile-total'));
		if (page >= total) {
			$('#loadMoreIndicator').remove();
			$('#loadMoreContainer').remove();
			return;
		}
		var pathname = location.pathname;
		var queryString = location.search.substring(1);
		
		var url = ctx + pathname +  '/fragment/more?page=' + page + '&' + queryString;
		
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
	
	logout : function() {
		var csrfParameter = $("meta[name='_csrf_parameter']").attr("content");
		var csrfToken = $("meta[name='_csrf']").attr("content");
		var url = ctx + '/sign-out';
		
		var form = $('<form>', {
			action: url,
			method: 'POST',
		}).append($('<input>', {
			name : csrfParameter,
			value : csrfToken,
			type : 'hidden'
		}));
		$('body').append(form);
		form.submit();
	}
};
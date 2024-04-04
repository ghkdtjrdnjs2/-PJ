	function showProfile() {
		const profile = $('#profile')[0].files[0];
		// $('#profile')[0] -> <input type='file' id='profile'>
		// file 속성을 가진 input 요소는 사용자가 선택한 파일을 files라는 배열 속성으로 저장한다
		// 파일을 하나 선택했다면 files[0]
	
		const maxSize = 1024 * 1024;
		if (profile.size > maxSize) {
			alert('사진 크기는 1MB이하여야 합니다');
			$('#profile')[0].files[0] = '';
		}
	
		let reader = new FileReader();
		// 파일을 로딩했을 때 동작할 콜백함수 지정
		reader.onload = function() {
			$('#show-profile').css('display', 'inline').attr('src', reader.result);
		}
	
		// 파일을 로딩한다
		reader.readAsDataURL(profile);
	}

  const ar = {
    username:[/^[A-Za-z0-9]{6,10}$/, '영숫자 6~10자입니다'],
    password:[/^[A-Za-z0-9]{8,10}$/, '영숫자 8~10자입니다'],
    email:[/^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i, '잘못된 이메일입니다'],
    birthday:[/^[0-9]{4}-[0-9]{2}-[0-9]{2}$/, '잘못된 날짜입니다']
  }

	// await를 사용하면 함수에 async를 붙여야한다
  async function usernameCheck() {
    const $element = $('#username');
    $element.next().text('');
    const value = $element.val();
    // 패턴비교
    if(ar.username[0].test(value)==false) {
      $element.next().text(ar.username[1]).attr('class', 'error')
      return false;
    }
    
    try {
    	const msg = await $.ajax('/member/id-check?username=' + value);
    	alert(msg);
    	$element.next().text('사용가능합니다').attr('class', 'success');
    	return true;
    } catch(err) {
			console.log(err);
			$element.next().text('사용중인 아이디입니다').attr('class', 'error');
			return false
		}
  }

  function passwordCheck() {
    const $element = $('#password');
    $element.next().text('');
    const value = $element.val();

    if(ar.password[0].test(value)==false) {
      $element.next().text(ar.password[1]).attr('class', 'error');
      return false;
    }
    return true;
  }

  function password2Check() {
    const password = $('#password').val();
    const $element = $('#password2');
    $element.next().text('');
    const value = $element.val();

    if(password!==value) {
      $element.next().text('새비밀번호가 일치하지 않습니다').attr('class', 'error');
      return false;
    }
    return true;
  }

  function emailCheck() {
    const $element = $('#email');
    $element.next().text('');
    const value = $element.val();

    if(ar.email[0].test(value)==false) {
      $element.next().text(ar.email[1]).attr('class', 'error');
      return false;
    }
    return true;
  }

  function birthdayCheck() {
    const $element = $('#birthday');
    $element.next().text('');
    const value = $element.val();

    if(ar.birthday[0].test(value)==false) {
      $element.next().text(ar.birthday[1]).attr('class', 'error');
      return false;
    }
    return true;
  }
		
  $(document).ready(function() {
		// 사진을 선택하면(change) 출력해라 
		$('#profile').on('change', showProfile);
		
    $('#username').on('blur', usernameCheck);
    $('#password').on('blur', passwordCheck);
    $('#password2').on('blur', password2Check);
    $('#email').on('blur', emailCheck);
    $('#birthday').on('blur', birthdayCheck);

    $('#join').on('click', async function() {
      const r1 = await usernameCheck();
      const r2 = passwordCheck();
      const r3 = password2Check();
      const r4 = emailCheck();
      const r5 = birthdayCheck();
      const result = r1 && r2 && r3 && r4 && r5;
      
      if(result==false) {
        return false;
      }
			$('#join_form').submit();
    })
  })

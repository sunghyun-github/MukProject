// Document가 준비되면 실행
window.addEventListener('DOMContentLoaded', function () {
    // 스와이퍼 객체 생성 및 초기화
    var swiper = new Swiper('.swiper', {
        // 슬라이드 형태 지정
        slidesPerView: 1,
        // 페이징 여부
        pagination: {
            el: '.swiper-pagination',
            type: 'progressbar',
            clickable: true
        },
        // 네비게이션 버튼 여부
        navigation: {
            nextEl: '.swiper-button-next ',
            prevEl: '.swiper-button-prev ',
        },
        // 스크롤바 여부
        scrollbar: {
            el: '.swiper-scrollbar',
        },
        autoplay: {
            delay: 6000,
            // 일시정지 버튼이 있을 경우 반드시 꺼야함
            disableOnInteraction: false,
        },
        // loop 옵션 활성화
        loop: true,
        effect: 'fade',
        speed: 2000,
    });

    // toggle_btn 클릭 이벤트 핸들러
    document.querySelector('.toggle_btn').addEventListener('click', function () {
        // toggle 클래스 토글
        this.classList.toggle('on');
        
        // autoplay 상태에 따라 동작 결정
        if (swiper.autoplay.running) {
            swiper.autoplay.stop();
        } else {
            swiper.autoplay.start();
        }
    });
    
    
    $(window).on('scroll', function () {
		const header = $('#header');
	    let st = $(window).scrollTop();
	
	    if (st >= 20) {
	      header.addClass('fixed');
	    } else {
	      header.removeClass('fixed');
	    }
	  });
  
	});

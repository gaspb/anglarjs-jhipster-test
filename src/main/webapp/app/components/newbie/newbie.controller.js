;(function() {
    'use strict';

    var app = angular
        .module('jhipsterElasticsearchSampleApplicationApp');
      app.controller('NewbieController', NewbieController);

    NewbieController.$inject = ['$state', '$compile', '$scope', 'LoginService'];

    function NewbieController($state, $compile, $scope, LoginService){

        var vm = this;
        vm.login = login;
        vm.timeOutRegister = timeOutRegister;
        vm.timeOutLogin = timeOutLogin;
        function login() {
            LoginService.open();
            vm.btnfocus = [false,false];
        }
        function timeOutLogin() {
            vm.ripple=false;
            setTimeout(login,800);
        }
        function timeOutRegister() {
            vm.ripple=true;
            setTimeout(function(){
                $state.go('register');
            },800);
        }

        this.mainOptions = {
            sectionsColor: ['#f5f5f5', '#c9462c', 'whitesmoke', '#0098b6', '#ccddff'],
            anchors: ['firstPage', 'secondPage', '3rdPage', '4thpage', 'lastPage'],
            navigation: true,
                navigationPosition: 'right',
            //Scrolling
            css3: true,
            scrollingSpeed: 1300,
            autoScrolling: true,
            fitToSection: true,
            fitToSectionDelay: 1000,
            scrollBar: false,
            easing: 'easeInOutCubic',
            easingcss3: 'ease',
            loopBottom: false,
            loopTop: false,
            loopHorizontal: true,
            continuousVertical: false,
            continuousHorizontal: true,
            scrollHorizontally: true,
            interlockedSlides: true,
            dragAndMove: true,
            offsetSections: false,
            resetSliders: false,
            fadingEffect: true,
            normalScrollElements: '#element1, .element2',
            scrollOverflow: false,
            scrollOverflowReset: false,
            scrollOverflowOptions: null,
            touchSensitivity: 15,
            normalScrollElementTouchThreshold: 5,
            bigSectionsDestination: null,

            //Accessibility
            keyboardScrolling: true,
            animateAnchor: true,
            recordHistory: true,

            //Design
            controlArrows: true,
            verticalCentered: true,

            paddingTop: '3em',
            paddingBottom: '10px',
            fixedElements: '#header, .footer',
            responsiveWidth: 0,
            responsiveHeight: 0,
            responsiveSlides: true,

            //Custom selectors
            sectionSelector: '.section',
            slideSelector: '.slide',

            lazyLoading: true,
        };

        this.moog = function(merg){ console.log(merg); };

        this.slides = [
            {
                title: 'Les Alertes - Engagez-vous en temps réel !',
                description: "Paramétrez en détail vos notifications pour avoir la main sur votre engagement.",
                sub:"Restez informé des projets nécessitant vos compétences !",
                src: ''
            },
            {
                title: "'A l'Echelle'",
                description: "Que vous ayez des compétences web, manuelles, artistiques, commerciales, ... l'Incubateur s'adapte et vous permet d'agir !",
                sub:"Les projets comme les compétences peuvent êtres déclarés commes localisés(ex: organiser une conférence) ou globaux(ex: FiscalKombat II). Certains projets, comme soutenir une association locale, peuvent demander à la fois des ressources locales (bras) et web. Ces dernières sont externalisables, vous pouvez ainsi à la déclaration d'un projet/ de vos compétences, définir chaque compétence comme locale ou globale",
                src: ''
            },
            {
                title: 'Explorez',
                description: 'Nous utilisons ElasticSearch, un outil de recherche très complet. ',
                sub:"Nous travaillons à l'intégration de nouveaux modes de visualisation des données (carte des compétences, nuage d'idées, lightbox de projets)",
                src: ''
            },
            {
                title: 'Réalisez',
                description: 'Nous implémenterons bientôt un logiciel de gestion de projet en interne. ',
                sub:"En attendant, nous vous conseillons une liste d'outils gratuits et autant que possible opensource pour travailler efficacement en équipe, sur le long terme et à distance.",
                src: ''
            },
            {
                title: 'Partagez',
                description: 'Formez-vous/formez grâce à notre système de tutorat !',
                sub:"En attendant le site à venir de l'Université Insoumise, nous proposons à chacun d'être tuteur dans une compétence qu'il maîtrise et d'être aidé dans la compétence qu'il souhaiterait apprendre.",
                src: ''
            },
            {
                title: 'Rencontrez',
                description: 'Travailler ensemble, ça crée des liens !',
                sub:"Si vous souhaitez continuer à travailler avec certaines personnes, nous proposons la possibilité de créer un groupe d'appui dématérialisé sur le site, avec des filtrages et outils personnalisés.",
                src: ''
            }
        ];

        this.addSlide = function() {
            vm.slides.push({
                title: 'New Slide',
                description: 'I made a new slide!',
                src: ''
            });

//$compile(angular.element($('.slide')))($scope);
        };
        vm.isDeployed = false;
        this.deployMenu=function(){
            vm.isDeployed = !this.isDeployed;
        };
        vm.btnfocus = [false, false];
        vm.ripple = false;
        this.rippleMe=function(i){
            vm.btnfocus[i] =! vm.btnfocus[i];
        };


    }

    app.directive ('rippleme', RippleMe);
    RippleMe.$inject= ['$animate'];
    function RippleMe($animate){
        return function (scope, element, attrs) {

            scope.$watch(attrs.rippleme, function(newVal){
                if(newVal){
                    $animate.addClass(element, "ripple")
                } else {
                    $animate.removeClass(element, "ripple")
                }
            })
        }
    }
    app.animation('.ripple', RippleAnim);
    function RippleAnim(){
        return {
            addClass: function(element, className){
                var isSafari = /constructor/i.test(window.HTMLElement);
                var isFF = !!navigator.userAgent.match(/firefox/i);

                if (isSafari) {
                    document.getElementsByTagName('html')[0].classList.add('safari');
                }
//BUTTON RIPPLE
                app.initRipple= function() {
                    var bt = document.querySelectorAll('.ripple')[0];


                    if (bt.classList.contains('rip-1')){
                        var turb = document.querySelectorAll('#filter-ripple-1 feImage')[0];
                        var dm = document.querySelectorAll('#filter-ripple-1 feDisplacementMap')[0];
                    }else {
                         turb = document.querySelectorAll('#filter-ripple-2 feImage')[0];
                          dm = document.querySelectorAll('#filter-ripple-2 feDisplacementMap')[0];
                    }
                    bt.addEventListener('click', function _func(e) {
                        TweenLite.set(turb, { attr: { x: isFF ? e.offsetX : e.offsetX + 10, y: isFF ? e.offsetY : e.offsetY + 10, width: 0, height: 0 } });
                        TweenLite.to(turb, 3, { attr: { x: '-=300', y: '-=300', width: 600, height: 600 } });
                        TweenLite.fromTo(dm, 2, { attr: { scale: 30 } }, { attr: { scale: 0 } });
                        this.removeEventListener('click', _func);
                    })};


                app.initRipple();


            }
            ,removeClass: function(){
                app.stopRipple = function(){


                };
            }

        }



    }
    app.directive ('deployme', DeployMe);

    DeployMe.$inject= ['$animate'];
    function DeployMe($animate){
        return function (scope, element, attrs) {

            scope.$watch(attrs.deployme, function(newVal){
                if(newVal){
                    $animate.addClass(element, "deploy")
                }else {
                    $animate.removeClass(element, "deploy")
                }
            })
        }
    }
    app.animation('.deploy', DeployAnim);
    function DeployAnim(){
        return {
            addClass: function(){

                app.$shareButtons=$(".share-button");
                app.$toggleButton=$(".share-toggle-button");
                app.buttonsNum=app.$shareButtons.length;
                app.buttonsMid=(app.buttonsNum/2);
                app.spacing=75;
                app.openShareMenu =function(){
                    TweenMax.to(app.$toggleButton,0.1,{
                        scaleX:1.2,
                        scaleY:0.6,
                        ease:Quad.easeOut,
                        onComplete:function(){
                            TweenMax.to(app.$toggleButton,.8,{
                                scale:0.6,
                                ease:Elastic.easeOut,
                                easeParams:[1.1,0.6]
                            });
                            TweenMax.to(app.$toggleButton.children(".share-icon"),.8,{
                                scale:1.4,
                                ease:Elastic.easeOut,
                                easeParams:[1.1,0.6]
                            })
                        }
                    });
                    app.$shareButtons.each(function(i){
                        var $cur=$(this);
                        var pos=i-app.buttonsMid;
                        if(pos>=0) pos+=1;
                        var dist=Math.abs(pos);
                        $cur.css({
                            zIndex:app.buttonsMid-dist
                        });
                        TweenMax.to($cur,1.1*(dist),{
                            x:pos*app.spacing,
                            scaleY:0.6,
                            scaleX:1.1,
                            ease:Elastic.easeOut,
                            easeParams:[1.01,0.5]
                        });
                        TweenMax.to($cur,.8,{
                            delay:(0.2*(dist))-0.1,
                            scale:0.6,
                            ease:Elastic.easeOut,
                            easeParams:[1.1,0.6]
                        });

                        TweenMax.fromTo($cur.children(".share-icon"),0.2,{
                            scale:0
                        },{
                            delay:(0.2*dist)-0.1,
                            scale:1,
                            ease:Quad.easeInOut
                        })
                    })
                };
                app.openShareMenu();


            }
            ,removeClass: function(){
                app.closeShareMenu = function(){
                    TweenMax.to([app.$toggleButton,app.$toggleButton.children(".share-icon")],1.4,{
                        delay:0.1,
                        scale:1,
                        ease:Elastic.easeOut,
                        easeParams:[1.1,0.3]
                    });
                    app.$shareButtons.each(function(i){
                        var $cur=$(this);
                        var pos=i-app.buttonsMid;
                        if(pos>=0) pos+=1;
                        var dist=Math.abs(pos);
                        $cur.css({
                            zIndex:dist
                        });

                        TweenMax.to($cur,0.4+((app.buttonsMid-dist)*0.1),{
                            x:0,
                            scale:.95,
                            ease:Quad.easeInOut,
                        });

                        TweenMax.to($cur.children(".share-icon"),0.2,{
                            scale:0,
                            ease:Quad.easeIn
                        });
                    });
                };
                app.closeShareMenu();
            }

        }
    }

})();

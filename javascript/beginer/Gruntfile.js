module.exports = function(grunt) {
    grunt.initConfig({
        pkg: grunt.file.readJSON("package.json"),
        jshint: {
            files: ["Gruntfile.js", "scripts/*.js", "scripts/page/*.js"]
        },
        concat: {
            options: {
                separator: ";"
            },
            dist: {
                src: ["scripts/vendor/*.js", "scripts/*.js", "scripts/**/*.js"],
                dest: "build/tmp/<%= pkg.name %>.js"
            }
        },
        babel: {
            options: {
                sourceMap: true,
                presets: ['babel-preset-es2015']
            },
            dist: {
                files: {
                    'build/tmp/app-babel.js': '<%= concat.dist.dest %>'
                }
            }
        },
        uglify: {
            options: {
                banner: '/*! <%= pkg.name %> <%= grunt.template.today("dd-mm-yyyy") %> */\n'
            },
            dist: {
                files: {
                    'build/scripts/<%= pkg.name %>.min.js': ['build/tmp/app-babel.js']
                }
            }
        },
        cssmin: {
            options: {
                shorthandCompacting: false,
                roundingPrecision: -1
            },
            target: {
                files: {
                    'build/styles/<%= pkg.name %>.min.css': ['styles/*.css']
                }
            }
        },
        htmlmin: {
            dist: {
                options: {
                    removeComments: true,
                    collapseWhitespace: true
                },
                files: {
                    'build/pages/bar_fill.html': 'pages/bar_fill.html', 
                    'build/index.html': 'index.html', 
                    'build/pages/mix.html': 'pages/mix.html', 
                    'build/pages/shoot_plane.html': 'pages/shoot_plane.html'
                }
            }
        },
        copy: {
            main: {
                files : [
                    {expand: true, src: ['images/*'], dest: 'build/'},
                    {expand: true, src: ['resources/*'], dest: 'build/'},
                    {expand: true, src: ['scripts/lib/*'], dest: 'build/'}
                ]
            }
        },
        watch: {
          files: ['*.html', 'pages/*.html', 'scripts/*.js', 'scripts/**/*.js', 'styles/*.css'],
          tasks: ['jshint', 'concat', 'uglify', 'cssmin', 'htmlmin']
        }
    });

    grunt.loadNpmTasks('grunt-contrib-jshint');
    grunt.loadNpmTasks('grunt-contrib-concat');
    grunt.loadNpmTasks('grunt-babel');
    grunt.loadNpmTasks('grunt-contrib-uglify');
    grunt.loadNpmTasks('grunt-contrib-cssmin');
    grunt.loadNpmTasks('grunt-contrib-htmlmin');
    grunt.loadNpmTasks('grunt-contrib-copy');
    grunt.loadNpmTasks('grunt-contrib-watch');

    grunt.registerTask('default', ['jshint', 'concat', 'babel', 'uglify', 'cssmin', 'htmlmin', 'copy', 'watch']);
};
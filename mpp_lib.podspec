Pod::Spec.new do |spec|
    spec.name                     = 'mpp_lib'
    spec.version                  = '1.2.5-SNAPSHOT'
    spec.homepage                 = 'https://github.com/RyuNen344/mpp-lib-test'
    spec.source                   = { :git => "git@github.com:RyuNen344/mpp-lib-test.git", :tag => "Cocoapods/#{spec.name}/#{spec.version}" }
    spec.authors                  = 'RyuNen344'
    spec.license                  = ''
    spec.summary                  = 'CocoaPods Test of Kotlin/Native module'

    spec.static_framework         = true
    spec.vendored_frameworks      = "build/cocoapods/framework/mpp-lib.framework"
    spec.libraries                = "c++"
    spec.module_name              = "#{spec.name}_umbrella"



    spec.pod_target_xcconfig = {
        'KOTLIN_TARGET[sdk=iphonesimulator*]' => 'ios_x64',
        'KOTLIN_TARGET[sdk=iphoneos*]' => 'ios_arm',
        'KOTLIN_TARGET[sdk=watchsimulator*]' => 'watchos_x86',
        'KOTLIN_TARGET[sdk=watchos*]' => 'watchos_arm',
        'KOTLIN_TARGET[sdk=appletvsimulator*]' => 'tvos_x64',
        'KOTLIN_TARGET[sdk=appletvos*]' => 'tvos_arm64',
        'KOTLIN_TARGET[sdk=macosx*]' => 'macos_x64'
    }

    spec.script_phases = [
        {
            :name => 'Build mpp_lib',
            :execution_position => :before_compile,
            :shell_path => '/bin/sh',
            :script => <<-SCRIPT
                set -ev
                REPO_ROOT="$PODS_TARGET_SRCROOT"
                "$REPO_ROOT/gradlew" -p "$REPO_ROOT" ::syncFramework \
                    -Pkotlin.native.cocoapods.target=$KOTLIN_TARGET \
                    -Pkotlin.native.cocoapods.configuration=$CONFIGURATION \
                    -Pkotlin.native.cocoapods.cflags="$OTHER_CFLAGS" \
                    -Pkotlin.native.cocoapods.paths.headers="$HEADER_SEARCH_PATHS" \
                    -Pkotlin.native.cocoapods.paths.frameworks="$FRAMEWORK_SEARCH_PATHS"
            SCRIPT
        }
    ]
end

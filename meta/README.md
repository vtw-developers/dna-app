# meta 파일 명명 규칙
#### 1. Camel YAML DSL 문법으로 작성한 파일명은 [Flow ID].camel.yaml 형식으로 작성한다.
#### 2. DnA의 Flow ID는 Apache Camel의 Route ID와 거의 동일하다.
#### 3. Camel 문법이 아닌 파일명은 *.camel.yaml 형식으로 작성하면 안된다.

# meta 폴더별 용도
## 1. general
   * General Flows : 템플릿화 되지 않은 Camel Route 파일
   * 주로 공통 기능에 대한 Camel Route를 구현 (예: 로그, 에러 처리 등)
   * 상황에 따라 프로젝트 개발자 또는 연구소 개발자가 개발
## 2. rest
   * Camel Rest Definitions : Rest API Endpoint 정의 (예: URL, 파라미터 정보 등)
   * Camel Rest DSL로 작성
   * 프로젝트 개발자가 주로 개발
## 3. sample
   * 교육용 샘플 Camel Routes
   * 추후 삭제
## 4. template
   * Flow Templates : 템플릿 용도로 사용되는 Camel Route 파일
   * [Template ID].camel.yaml 형식으로 파일명을 작성한다.
   * 상황에 따라 프로젝트 개발자 또는 연구소 개발자가 개발
## 5. templated
   * Templated Flows : 템플릿 파라미터 정보를 담은 파일. Camel DSL 문법이 아니다.
   * [Flow ID].templated.yaml 형식으로 파일명을 작성한다.
   * 프로젝트 개발자가 주로 개발
   * 개발할 API 하나 당 파일 1개라고 보면 된다. 

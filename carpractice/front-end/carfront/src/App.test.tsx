// Vitest에서 테스트 관련 함수들을 가져옴
// describe: 테스트 그룹을 묶는 함수
// test: 개별 테스트 케이스 정의
// expect: 결과를 검증하는 함수
import { describe, expect, test } from 'vitest'

// React 컴포넌트를 테스트하기 위한 도구
// render: 컴포넌트를 가상 DOM에 렌더링
// screen: 렌더링된 화면에서 요소를 찾기 위한 객체
import { render, screen } from '@testing-library/react'

// 테스트 대상이 되는 App 컴포넌트
import App from './App';

// jest-dom의 matcher 확장 기능을 Vitest에서 사용 가능하게 해줌
// 예: toBeInTheDocument(), toHaveTextContent() 등
import '@testing-library/jest-dom/vitest'

// "App tests"라는 이름의 테스트 그룹 생성
describe("App tests", () => {

    // "component renders"라는 테스트 케이스 정의
    test("component renders", () => {

        // App 컴포넌트를 가상 DOM(jsdom 환경)에 렌더링
        // 실제 브라우저는 아니고 테스트용 가짜 브라우저 환경
        render(<App />);

        // 화면에서 "Car Shop"이라는 텍스트를 찾음
        // /Car Shop/i 는 정규표현식
        // i 옵션은 대소문자 구분 안함
        const element = screen.getByText(/Car Shop/i);

        // 찾은 요소가 실제로 문서에 존재하는지 검증
        // 존재하지 않으면 테스트 실패
        expect(element).toBeInTheDocument();
    });

});
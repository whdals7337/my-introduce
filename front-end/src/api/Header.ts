export type Header<T> = {
    transactionTime: string; //api 통신시간
    resultCode: string; //api 응답코드
    description: string; //api 부가설명
    data: T;
    pagination: Pagination;
}

export type Pagination = {
    totalPages: number;
    totalElements: number;
    currentPage: number;
    currentElements: number;
}
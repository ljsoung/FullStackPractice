import { render, screen } from "@testing-library/react";
import { describe, expect, test } from "vitest";
import Carlist from "./components/Carlist";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";

const queryClient = new QueryClient({
    defaultOptions: {
        queries: {
            retry: false,
        },
    },
});

const wrapper = ({ children }: { children: React.ReactNode }) => (
  <QueryClientProvider client={queryClient}>
    {children}
  </QueryClientProvider>
);

describe("Carlist tests", () => {
    test("component renders", () => {
        render(<Carlist />, {wrapper});
        expect(screen.getByText(/Loading/i)).toBeInTheDocument();
    })
});